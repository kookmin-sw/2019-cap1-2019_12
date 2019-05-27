from predict_utils import tokenizer, openStopword, vectorize
from sklearn.metrics.pairwise import cosine_similarity
import json
import numpy as np
import operator


def openModel(filename):
    import joblib
    model = joblib.load(filename)
    return model


#   예측 후 핵심역량 이름으로 return
def SVCpredict(model, text):
    keyword_names = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']
    result = model.predict([text])
    return keyword_names[result[0]-1]


#   분석 후 dictionary 형태로 return
def SVCproba(model, text):
    proba = model.predict_proba([text]).tolist()[0]
    proba = [proba[i] for i in range(10)]
    keyword_eng = ['global', 'active', 'challenge', 'sincerity', 'communication', 'patient', 'honesty', 'responsibility', 'creative', 'teamwork']
    
    result = {}
    for i in range(10):
        result[keyword_eng[i]] = float(proba[i]*100)
    
    return result


def Cosine(company_dict, company, user_text_dict):
    # cos = cosine_similarity(company_dict, user_text)
    cosine_dict = {}
    user_text = []

    for key in user_text_dict.keys():
        user_text.append(user_text_dict[key])

    for i in range(len(company_dict)):
        x = np.array(company_dict[company[i]]).reshape(1, -1)
        y = np.array(user_text).reshape(1, -1)
        cosine_dict[company[i]] = cosine_similarity(x, y).tolist()[0][0]

    cosine_dict = sorted(cosine_dict.items(), key=operator.itemgetter(1), reverse=True)

    return dict(cosine_dict[i] for i in range(3))


def Holland(user_text_dict):
    holland_type = dict()
    holland_type['S'] = (user_text_dict['global'] + user_text_dict['communication'] + user_text_dict['teamwork'])/3
    holland_type['E'] = (user_text_dict['challenge']+user_text_dict['responsibility']+user_text_dict['active']+user_text_dict['creative'])/4
    holland_type['C'] = (user_text_dict['patient']+user_text_dict['honesty']+user_text_dict['sincerity'])
    #keyword_eng = ['global', 'active', 'challenge', 'sincerity', 'communication', 'patient', 'honesty', 'responsibility', 'creative', 'teamwork']
    # S: 소통, 팀워크, 글로벌
    # E: 도전, 주인의식, 능동, 창의
    # C: 인내심, 정직, 성실
    return holland_type


def Percent(company_dict, user_text_dict):
    result = dict()

    i = 0
    for key, value in user_text_dict.items():
        result[key] = (value / company_dict[i])*100
        i += 1

    return result


def companyPredict(model, text, company):
    #CJ / LG / SK / 현대 / 삼성
    predict_company = model.decision_function([text]).tolist()
    predict_company_dict = {}

    for i in range (len(company)) :
        predict_company_dict[company[i]] = predict_company[0][i]

    predict_company_dict = sorted(predict_company_dict.items(), key=operator.itemgetter(1), reverse=True)
    predict_company_dict = dict(predict_company_dict)

    return (predict_company_dict)


def Predict(text):
    model = openModel('SVC_PROB.joblib')
    company_model = openModel('Company_SVM.joblib')


    keyword_names = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']
    job = ['architecture', 'IT', 'management', 'production', 'sales']
    company = ['samsung', 'hyundai', 'LG', 'SK', 'CJ']

    #테스트데이터
    company_dict = {
        'samsung': [2.829048, 21.278193, 12.713069, 30.096896, 5.50489, 4.997317, 2.318836, 10.240305, 4.814532, 5.206915],
        'hyundai': [2.277059, 17.469233, 11.893394, 31.895603, 5.207689, 4.522994, 2.957843, 13.852678, 5.115702, 4.807805],
        'LG': [4.691135, 35.682092, 9.088129, 18.448958, 3.483393, 6.277644, 1.17589, 11.276273, 4.630043, 5.246442],
        'SK': [2.95132, 17.854298, 12.35037, 31.950111, 5.126333, 5.243466, 2.778728, 11.823368, 4.841313, 5.080694],
        'CJ': [3.732902, 21.483846, 12.301521, 30.066666, 5.893883, 4.261314, 2.306606, 11.011743, 4.632992, 4.308527]
    }
    job_dict = {
        'architecture': [2.829048, 21.278193, 12.713069, 30.096896, 5.50489, 4.997317, 2.318836, 10.240305, 4.814532, 5.206915],
        'IT': [2.277059, 17.469233, 11.893394, 31.895603, 5.207689, 4.522994, 2.957843, 13.852678, 5.115702, 4.807805],
        'management': [4.691135, 35.682092, 9.088129, 18.448958, 3.483393, 6.277644, 1.17589, 11.276273, 4.630043, 5.246442],
        'production': [2.95132, 17.854298, 12.35037, 31.950111, 5.126333, 5.243466, 2.778728, 11.823368, 4.841313, 5.080694],
        'sales': [3.732902, 21.483846, 12.301521, 30.066666, 5.893883, 4.261314, 2.306606, 11.011743, 4.632992, 4.308527]
    }

    result = dict()

    result['user'] = SVCproba(model, text)
    #result['Cosine'] = Cosine(company_dict, company, result['user'])
    result['Holland'] = Holland(result['user'])
    result['company'] = companyPredict(company_model, text, company) # 그래프 표현할 값 조정 필요함.
    result['choice_company'] = Percent(company_dict['samsung'], result['user']) # 나중에 기업명을 DB에서 받아와야 함.
    result['first_company'] = Percent(company_dict[list(result['company'].keys())[0]], result['user'])


    for key, value in result.items():
        for key_, value_ in value.items():
            value[key_] = format(value_, '.30f')

    return json.dumps(result)


if __name__ == "__main__":
    print(Predict("예측자기소개서"))
