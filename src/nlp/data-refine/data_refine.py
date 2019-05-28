import konlpy
from konlpy.tag import Okt
import json
import os
import glob

# 자소서가 저장되어있는 경로 저장.
train_path = 'dataset/train/saramin'

# 형태소 분석 결과를 가져오기 위함.
okt = Okt()

# 자기소개서를 load하는 함수.
def read_data(filename):
    with open(filename, 'r') as f:
        json_data = json.load(f)
        one_data = [json_data[i] for i in json_data]
    return one_data

# train_data 리스트를 만들고 json확장자 자기소개서를 한 곳에 모은다.
train_data = list()
for filename in glob.glob(os.path.join(train_path,'*.json')):
    one_data = read_data(filename)
    train_data.append(one_data)

def tokenize(doc):
    return [t for t in okt.nouns(doc)]

if os.path.isfile('dataset/docs/train_docs.json'):
    with open('dataset/docs/train_docs.json', encoding="UTF-8") as f:
        train_docs = json.load(f)
else:
    train_docs = [(row[0], row[1], tokenize(row[2])) for row in train_data]
    # JSON 파일로 저장
    with open('dataset/docs/train_docs.json', 'w', encoding="UTF-8") as make_file:
        json.dump(train_docs, make_file, ensure_ascii=False, indent="\t")
