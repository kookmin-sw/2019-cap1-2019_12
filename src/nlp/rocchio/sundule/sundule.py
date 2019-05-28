import csv
import pandas as pd
import numpy as np
from konlpy.tag import Okt
from sklearn.metrics.pairwise import cosine_similarity, linear_kernel


# 함수 1: 토큰화 함수 정의.
def tokenizer(raw, stopword, pos=["Noun","Verb"]):
    okt = Okt()
    
    return [
            word for word, tag in okt.pos(
                                          raw,
                                          norm=True,   # normalize 정제 과정
                                          stem=True    # stemming 정제 과정
                                          )
            if len(word) > 1 and tag in pos and word not in stopword
            ]


# 함수 2: 데이터 리딩 함수 정의.
def read_data(filename):
    with open(filename, 'r') as f:
        data = f.read()
        # remove_quotes
        data = data.replace("‘", " ")
        data = data.replace("’", " ")
        data = data.replace("“", " ")
        data = data.replace("”", " ")
        data = data.replace("`", " ")
        data = data.replace("\'", " ")
        data = data.replace("\"", " ")
    return data


# 함수 3: tf-idf 점수 결정하는 함수 정의.
def key_score(resume):
    # tf-idf값 불러옴.
    X = pd.read_csv('dataset/keywords/vector_tfidf.csv', header=None)
    X = np.array(X)
    
    # 학습 단어(feature) 불러옴.
    f = open('dataset/keywords/vector_features.csv', 'r', encoding='utf-8')
    reader = csv.reader(f)
    features = list()
    for i in reader:
        features.append(i[1])

    # 불용어리스트 불러옴.
    f = open('dataset/stopwords/stopwords.csv', 'r', encoding='utf-8')
    reader = csv.reader(f)
    stopwords = list()

for row in reader:
    stopwords.append(row[0])
    
    resume_data = resume
    srch = [t for t in tokenizer(resume_data, stopwords) if t in features]
    
    # document term matrix(dtm) 에서 검색하고자 하는 feature만 뽑아낸다.
    srch_dtm = np.asarray(X)[:, [
                                 features.index(i) for i in srch
                                 ]]
                                 score = srch_dtm.sum(axis=1)
                                 return score


# 함수 4: 유사도 비교하는 함수 정의.
def cos_sim(user_score):
    
    f = pd.read_csv('dataset/train/CJ_score.csv', header=None, skiprows=[0])
    cj_score = np.array(f)
    f = pd.read_csv('dataset/train/LG_score.csv', header=None, skiprows=[0])
    lg_score = np.array(f)
    f = pd.read_csv('dataset/train/HYUNDAI_score.csv', header=None, skiprows=[0])
    hyundai_score = np.array(f)
    f = pd.read_csv('dataset/train/SAMSUNG_score.csv', header=None, skiprows=[0])
    samsung_score = np.array(f)
    f = pd.read_csv('dataset/train/SK_score.csv', header=None, skiprows=[0])
    sk_score = np.array(f)
    
    #f = pd.read_csv('dataset/train/archit_score.csv', header=None, skiprows=[0])
    #archit_score = np.array(f)
    f = pd.read_csv('dataset/train/business_score.csv', header=None, skiprows=[0])
    business_score = np.array(f)
    f = pd.read_csv('dataset/train/it_score.csv', header=None, skiprows=[0])
    it_score = np.array(f)
    #f = pd.read_csv('dataset/train/manufact_score.csv', header=None, skiprows=[0])
    #manufact_score = np.array(f)
    #f = pd.read_csv('dataset/train/sales_score.csv', header=None, skiprows=[0])
    #sales_score = np.array(f)
    
    x = np.array(user_score)
    x = x / sum(x)
    
    x = x.reshape(1,-1)
    cj_score = cj_score.reshape(1,-1)
    lg_score = lg_score.reshape(1,-1)
    hyundai_score = hyundai_score.reshape(1,-1)
    samsung_score = samsung_score.reshape(1,-1)
    sk_score = sk_score.reshape(1,-1)
    
    #archit_score = archit_score.reshape(1,-1)
    business_score = business_score.reshape(1,-1)
    it_score = it_score.reshape(1,-1)
    #manufact_score = manufact_score.reshape(1,-1)
    #sales_score = sales_score.reshape(1,-1)
    
    result = list()
    result.append(round((cosine_similarity(x,cj_score)[0][0] - 0.9)*1000, 2))
    result.append(round((cosine_similarity(x,lg_score)[0][0] - 0.9)*1000, 2))
    result.append(round((cosine_similarity(x,hyundai_score)[0][0] - 0.9)*1000, 2))
    result.append(round((cosine_similarity(x,samsung_score)[0][0] - 0.9)*1000, 2))
    result.append(round((cosine_similarity(x,sk_score)[0][0] - 0.9)*1000, 2))
    
    result.append(round((cosine_similarity(x,business_score)[0][0] - 0.9)*1000, 2))
    result.append(round((cosine_similarity(x,it_score)[0][0] - 0.9)*1000, 2))
    
    print('CJ와 SK 자소서 코사인 유사도: {}%'.format(round((cosine_similarity(x,sk_score)[0][0] - 0.9)*1000, 1)))
    print('CJ와 현대 자소서 코사인 유사도: {}%'.format(round((cosine_similarity(x,hyundai_score)[0][0] - 0.9)*1000, 1)))
    print('CJ와 경영 자소서 코사인 유사도: {}%'.format(round((cosine_similarity(x,business_score)[0][0] - 0.9)*1000, 1)))
    
    return result

