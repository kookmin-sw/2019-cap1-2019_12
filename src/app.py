from flask import Flask,jsonify

import pickle
import joblib
from konlpy.tag import Okt
from sklearn.feature_extraction.text import TfidfVectorizer
import csv
import json
import jpype
from utils import *

# -*- coding: utf-8 -*-cd

app = Flask(__name__)


#   예측 후 핵심역량 이름으로 return
def SVCpredict(model, text):
    jpype.attachThreadToJVM()
    keyword_names = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']
    result = model.predict([text])
    return keyword_names[result[0] - 1]

#   decision function으로 예측 후 리스트 형태로 return
def SVCdecision(model, text):
    jpype.attachThreadToJVM()
    return model.decision_function([text]).tolist()[0]

#   분석 후 dictionary 형태로 return
def SVCproba(model, text):
    jpype.attachThreadToJVM()
    proba = model.predict_proba([text]).tolist()[0]
    proba = [format(proba[i], '.30f') for i in range(10)]
    keyword_eng = ['global', 'active', 'challenge', 'sincerity', 'communication', 'patient', 'honesty',
                   'responsibility', 'creative', 'teamwork']
    result = {}
    for i in range(10):
        result[keyword_eng[i]] = proba[i]
    return result

#   분석 후 json으로 저장
def UserAnalysis(model, text):
    jpype.attachThreadToJVM()
    return json.dumps(SVCproba(model, text))

def openModel():
    jpype.attachThreadToJVM()
    filename = 'SVC_PROB.joblib'
    svc_from_joblib = joblib.load(filename)
    return svc_from_joblib

# svc_from_joblib = openModel();
svc_from_joblib = openModel();

@app.route('/')
def PredictMain():
    jpype.attachThreadToJVM()
    #   keyword_names = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']
    #   job = ['architecture', 'IT', 'management', 'production', 'sales']
    #   company = ['samsung', 'hyundai', 'LG', 'SK', 'CJ']
    # str 형태

    result = UserAnalysis(svc_from_joblib, "1. LG유플러스 및 분야/직무에 대한 지원 동기와 해당 분야/직무를 잘 수행하기 위해 어떤 준비를 해왔는지 구체적으로 설명해주세요. [책임감을 갖고 역량을 펼칠 수 있는 곳] LG유플러스는 제가 갖춘 능력을 활용하여 새로운 가치를 창출할 수 있는 곳이라고 생각합니다. 현재 LG유플러스는 우리나라의 통신업을 이끌어 가고 있습니다. 통신업을 이끌어가는 선두주자로서 4차 산업혁명의 중심에 서 있기도 합니다. 소비자와 직접 연관이 있는 스마트폰, 인터넷과 같은 사업에 지속적인 투자를 통해 미래시장에서 우위를 점하려고 노력하고 있기도 합니다. 또한, 세상의 변화에 기민하게 대처하여 우리가 생각해 본 적 없는 방식의 서비스를 제공하려 하고 있는 것으로 알고있습니다. 저는 이러한 LG유플러스의 신념과 동행하여 함께 성장할 수 있다고 생각하여 지원하게 되었습니다. [신뢰받는 엔지니어가 되겠습니다] NW운영 부서는 고객과의 지속적인 커뮤니케이션을 통해 네트워크 운영에서 발생할 수 있는 이슈를 예방하고 해결해야 합니다. 또한, 이슈 발생 시 정해진 시간 내에 최고의 방법을 도출하여 손실을 최소화해야 하므로, 엔지니어로서 필요한 역량은 협업 능력, 문제해결능력이라고 생각합니다. 저는 재학시절 다수의 프로젝트 경험과 회사생활을 하면서 직무역량을 향상하고자 노력했습니다. 첫째, 연구실 생활과 팀 프로젝트를 통해 협업능력을 기를 수 있었습니다. 짧게는 2주, 길게는 6개월 동안 프로젝트를 진행하면서 커뮤니케이션 역량을 발휘하여 팀원에게 신뢰를 얻은 경험이 있습니다. 이 경험을 바탕으로, 실제 제품을 제작하여 전기학회에 출품하였고, 특허도 등록하여 좋은 결과를 얻을 수 있었습니다. 둘째, 문제해결능력입니다. 짧지만, 전력회사에서 근무하면서 개발 프로세스를 배웠고, 여러 부서와 부딪히며 업무를 수행했습니다. 업무를 수행하면서 장비의 결함, 협력사와의 갈등 등이 있었지만, 팀원들과 함께 해결하여 끝까지 완수할 수 있었습니다. 이러한 역량을 바탕으로 불필요한 손실 없이 문제를 해결하여 LG유플러스의 이익증대 기여에 노력하겠습니다.")
    return jsonify(result)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
