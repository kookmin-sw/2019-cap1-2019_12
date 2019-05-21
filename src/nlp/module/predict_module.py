import pickle
import joblib
from konlpy.tag import Okt
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.svm import SVC
from sklearn.pipeline import Pipeline
from sklearn.multiclass import OneVsRestClassifier
import csv
import operator

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


def SVCpredict(model, text):
    keyword_names = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']
    result = model.predict([text])
    return keyword_names[result[0]-1]


def SVCdecision(model, text):
    return model.decision_function([text]).tolist()[0]


def SVCproba(model, text):
    return model.predict_proba([text]).tolist()[0]


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

    #keyword_names = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']
    #job = ['architecture', 'IT', 'management', 'production', 'sales']
    #company = ['samsung', 'hyundai', 'LG', 'SK', 'CJ']

    print(SVCproba(svc_from_joblib, "열정을 갖고 끊임없이 노력하는 사람"))
    print(SVCpredict(svc_from_joblib, "열정을 갖고 끊임없이 노력하는 사람"))
