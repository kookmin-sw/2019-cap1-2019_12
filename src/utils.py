from konlpy.tag import Okt
from sklearn.feature_extraction.text import TfidfVectorizer
import csv
import jpype
okt = Okt()


def openStopword():
    jpype.attachThreadToJVM()
    f = open('stopwords.csv', 'r', encoding='utf-8')
    reader = csv.reader(f)
    stopwords = list()

    for row in reader:
        stopwords.append(row[0])

    return stopwords


def tokenizer(raw, pos=["Noun","Verb"], stopword=openStopword()):
    jpype.attachThreadToJVM()
    return [
        word for word, tag in okt.pos(
            raw,
            norm=True,   # normalize 정제 과정
            stem=True    # stemming 정제 과정
            )
            if len(word) > 1 and tag in pos and word not in stopword
        ]


vectorize = TfidfVectorizer(
    ngram_range=(1, 3), #n-gram 3
    tokenizer=tokenizer,
    max_df=0.95,
    min_df=0.02, # 100개 문서 중 1번만 등장하면 제외
    sublinear_tf=True
)