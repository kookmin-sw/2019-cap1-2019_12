import pickle
import joblib
from konlpy.tag import Okt
from sklearn.feature_extraction.text import TfidfVectorizer
import csv
import json


def openStopword():
    f = open('stopwords.csv', 'r', encoding='utf-8')
    reader = csv.reader(f)
    stopwords = list()

    for row in reader:
        stopwords.append(row[0])

    return stopwords


def tokenizer(raw, pos=["Noun", "Verb"], stopword=openStopword()):
    okt = Okt()
    return [
        word for word, tag in okt.pos(
            raw,
            norm=True,
            stem=True
            )
            if len(word) > 1 and tag in pos and word not in stopword
        ]


#   예측 후 핵심역량 이름으로 return
def SVCpredict(model, text):
    keyword_names = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']
    result = model.predict([text])
    return keyword_names[result[0]-1]


#   decision function으로 예측 후 리스트 형태로 return
def SVCdecision(model, text):
    return model.decision_function([text]).tolist()[0]


#   분석 후 dictionary 형태로 return
def SVCproba(model, text):
    proba = model.predict_proba([text]).tolist()[0]
    proba = [format(proba[i], '.30f') for i in range(10)]
    keyword_eng = ['global', 'active', 'challenge', 'sincerity', 'communication', 'patient', 'honesty', 'responsibility', 'creative', 'teamwork']
    result = {}
    for i in range(10):
        result[keyword_eng[i]] = proba[i]
    return result


#   분석 후 json으로 저장
def UserAnalysis(model, text):
    return json.dumps(SVCproba(model, text))


if __name__ == "__main__":
    vectorize = TfidfVectorizer(
        ngram_range=(1, 3),  # n-gram 3
        tokenizer=tokenizer,
        max_df=0.95,
        min_df=0,
        sublinear_tf=True
    )

    filename = 'SVC_PROB.joblib'
    svc_from_joblib = joblib.load(filename)

#   keyword_names = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']
#   job = ['architecture', 'IT', 'management', 'production', 'sales']
#   company = ['samsung', 'hyundai', 'LG', 'SK', 'CJ']

    print(SVCproba(svc_from_joblib, "열정을 갖고 끊임없이 노력하는 사람"))
    print(SVCpredict(svc_from_joblib, "열정을 갖고 끊임없이 노력하는 사람"))

    print(UserAnalysis(svc_from_joblib, "열정을 갖고 끊임없이 노력하는 사람"))
