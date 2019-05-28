# -*- coding: utf-8 -*-
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
    return keyword_names[result[0] - 1]


#   분석 후 dictionary 형태로 return
def SVCproba(model, text):
    proba = model.predict_proba([text]).tolist()[0]
    proba = [proba[i] for i in range(10)]
    keyword_eng = ['global', 'active', 'challenge', 'sincerity', 'communication', 'patient', 'honesty',
                   'responsibility', 'creative', 'teamwork']

    result = {}
    for i in range(10):
        result[keyword_eng[i]] = float(proba[i] * 100)

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
    holland_type['S'] = (user_text_dict['global'] + user_text_dict['communication'] + user_text_dict['teamwork']) / 3
    holland_type['E'] = (user_text_dict['challenge'] + user_text_dict['responsibility'] + user_text_dict['active'] +
                         user_text_dict['creative']) / 4
    holland_type['C'] = (user_text_dict['patient'] + user_text_dict['honesty'] + user_text_dict['sincerity'])
    # keyword_eng = ['global', 'active', 'challenge', 'sincerity', 'communication', 'patient', 'honesty', 'responsibility', 'creative', 'teamwork']
    # S: 소통, 팀워크, 글로벌
    # E: 도전, 주인의식, 능동, 창의
    # C: 인내심, 정직, 성실
    return holland_type


def Percent(company_dict, user_text_dict):
    result = dict()

    i = 0
    for key, value in user_text_dict.items():
        result[key] = (value / company_dict[i]) * 100
        i += 1

    return result


def companyPredict(model, text, company):
    # CJ / LG / SK / 현대 / 삼성
    predict_company = model.decision_function([text]).tolist()
    predict_company_dict = {}

    for i in range(len(company)):
        predict_company_dict[company[i]] = predict_company[0][i]

    predict_company_dict = sorted(predict_company_dict.items(), key=operator.itemgetter(1),
                                  reverse=True)  # 값이 큰 순서대로 정렬

    last_company_value = float(predict_company_dict[4][1])  # 튜플형식임. 5위 값의 2번재 값 저장

    predict_company_dict = dict(predict_company_dict)  # 튜플을 딕셔너리 형태로 변환

    sum = 0

    # 모든 값이 양수면 값 변환 안해도 됨. 따라서 마지막 값만 체크하고, 그 값이 양수가 아니라면(=모든 값이 음수라는 뜻) 값 변환 해야함

    if (last_company_value < 0):  # 변환 해야함
        for i in range(0, len(company)):  # - 값 없애기
            tmp = float(predict_company_dict[company[i]])
            tmp += abs(last_company_value)
            tmp *= 100
            sum += tmp
            predict_company_dict[company[i]] = tmp
    else:
        for i in range(0, len(company)):  # -값이 없는 경우에 총합만 저장
            tmp = float(predict_company_dict[company[i]]) * 100
            sum += tmp
            predict_company_dict[company[i]] = tmp

    for i in range(0, len(company)):  # 총합을 이용하여 퍼센트 계산
        tmp = float(predict_company_dict[company[i]])
        tmp = (tmp * 100) / sum
        predict_company_dict[company[i]] = tmp

    return (predict_company_dict)


def Predict(*text):
    model = openModel('KeywordModel.joblib')
    company_model = openModel('CompanyModel.joblib')

    keyword_names = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']
    job = ['architecture', 'IT', 'management', 'production', 'sales']
    company = ['samsung', 'hyundai', 'LG', 'SK', 'CJ']

    company_dict = {
        'samsung': [10.952462, 13.259205, 12.329018999999999, 21.309497999999998, 9.510311, 6.0389669999999995, 2.3193330000000003, 15.3345, 3.4741220000000004, 5.472583],
        'hyundai': [11.289582, 12.711838, 14.681745, 19.719708, 9.164579, 5.763642, 1.8328939999999998, 17.878541000000002, 2.2700359999999997, 4.687436],
        'LG': [10.349413, 12.957435, 13.990820000000001, 19.228322000000002, 7.697866, 6.906366, 1.7987099999999998, 18.576876000000002, 2.920934, 5.573257],
        'SK': [10.402835000000001, 12.334364, 13.663704, 19.325575999999998, 9.732626, 6.8238259999999995, 1.5660830000000001, 16.734253, 2.9451910000000003, 6.471544],
        'CJ': [9.288477, 13.242901000000002, 13.745071000000001, 21.213103, 6.885591000000001, 7.7885860000000005, 1.776099, 17.291672, 3.67024, 5.098260000000001]
    }


    job_dict = {
        'architecture': [10.952462, 13.259205, 12.329018999999999, 21.309497999999998, 9.510311, 6.0389669999999995, 2.3193330000000003, 15.3345, 3.4741220000000004, 5.472583],
        'IT': [11.289582, 12.711838, 14.681745, 19.719708, 9.164579, 5.763642, 1.8328939999999998, 17.878541000000002, 2.2700359999999997, 4.687436],
        'management': [10.349413, 12.957435, 13.990820000000001, 19.228322000000002, 7.697866, 6.906366, 1.7987099999999998, 18.576876000000002, 2.920934, 5.573257],
        'production': [10.402835000000001, 12.334364, 13.663704, 19.325575999999998, 9.732626, 6.8238259999999995, 1.5660830000000001, 16.734253, 2.9451910000000003, 6.471544],
        'sales': [9.288477, 13.242901000000002, 13.745071000000001, 21.213103, 6.885591000000001, 7.7885860000000005, 1.776099, 17.291672, 3.67024, 5.098260000000001]
    }

    result = dict()
    text_all = ''

    for i in range(len(text)):
        text_all += text[i]
        result['q'+str(i+1)] = SVCproba(model, text[i])

    result['user'] = SVCproba(model, text_all)
    # result['Cosine'] = Cosine(company_dict, company, result['user'])
    result['Holland'] = Holland(result['user'])
    result['company'] = companyPredict(company_model, text_all, company)
    result['choice_company'] = Percent(company_dict['samsung'], result['user'])  # select_company : 선택 기업
    result['first_company'] = Percent(company_dict[list(result['company'].keys())[0]], result['user'])

    for key, value in result.items():
        for key_, value_ in value.items():
            value[key_] = format(value_, '.30f')

    return json.dumps(result)


def PredictMain():
    Predict()
    #print(Predict("예측텍스트","도전적인 사람","안녕하세요"))
