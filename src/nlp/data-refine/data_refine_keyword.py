import json
import os
import glob
from konlpy.tag import Okt

train_path = 'dataset/train'

def read_data(filename):
    with open(filename, 'r') as f:
        data = [line.split('\t') for line in f.read().splitlines()]
    return data

train_data = read_data('dataset/train/팀워크/teamwork.txt')

okt = Okt()

def tokenize(doc):
    return [t for t in okt.nouns(doc)]

if os.path.isfile('dataset/docs/train_docs.json'):
    with open('dataset/docs/train_docs.json', encoding="UTF-8") as f:
        train_docs = json.load(f)

else:
    train_docs = [(row[0], tokenize(row[1])) for row in train_data]
    # JSON 파일로 저장
    with open('dataset/docs/train_docs.json', 'w', encoding="UTF-8") as make_file:
        json.dump(train_docs, make_file, ensure_ascii=False, indent="\t")
