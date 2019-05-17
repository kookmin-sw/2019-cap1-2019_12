import pickle
import joblib
from konlpy.tag import Okt
from sklearn.multiclass import OneVsRestClassifier
from sklearn.svm import LinearSVC
from sklearn.pipeline import Pipeline
import csv
import operator

def openStopword():
    f = open('dataset/stopwords/stopwords.csv', 'r', encoding='utf-8')
    reader = csv.reader(f)
    stopwords = list()

    for row in reader:
        stopwords.append(row[0])

    return stopwords


def tokenizer(raw, pos=["Noun","Verb"], stopword=openStopword()):
    return [
        word for word, tag in okt.pos(
            raw,
            norm=True,
            stem=True
            )
            if len(word) > 1 and tag in pos and word not in stopword
        ]


def SVCpredict(model, text):
    result = model.predict([text])
    return keyword_names[result[0]-1]


def SVCdecision(model, text):
    return model.decision_function([text]).tolist()[0]


def Compare(type_dict,typename,decision):
    compare_dict = {}
    for i in range(len(type_dict)):
        tmp = []
        for j in range(len(decision)):
            tmp.append(abs(type_dict[typename[i]][j] - decision[j]))
        compare_dict[typename[i]] = sum(tmp)
    return sorted(compare_dict.items(), key=operator.itemgetter(1))


def predict_main():
	okt = Okt()
	vectorize = TfidfVectorizer(
	    ngram_range=(1,3), #n-gram 3
	    tokenizer=tokenizer,
	    max_df=0.95,
	    min_df=0,
	    sublinear_tf=True
	)

	filename = 'SVCmodel.joblib'
	svc_from_joblib = joblib.load(filename)

	keyword_names = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']
	job = ['architecture','IT','management','production','sales']
	company = ['samsung','hyundai','LG','SK','CJ']

	company_dict = {
	    'samsung': [-0.8887642959439978,  -0.2320497495038032,  -0.3980158364286989,  0.07982216179178525,  -0.4634689585655357,  -1.4945375211875371,  -1.5887466236154073,  -0.3994969557289842,  -1.9458319112295257,  -0.6715375781390972],
	    'hyundai': [-0.8228359594421046,  -0.4560047847307666,  -0.4392581998869604,  0.05151212010714745,  -0.37278093523020006,  -1.441515818729987,  -1.6143444997864906,  -0.554804611382156,  -1.831011837725321,  -0.6144309645563442],
	    'LG': [-0.7620625480990436,  -0.4474363044623253,  -0.46674841323118554,  0.07333865957640484,  -0.43207572092095403,  -1.4279317971574639,  -1.4718943563369267,  -0.6400563343545634,  -1.7959020562081858,  -0.7120585840815354],
	    'SK': [-0.7200478445244407,  -0.4990252126738345,  -0.4127369498124192,  0.006192507305919692,  -0.41839920312881207,  -1.4965564622475518,  -1.4192891756133306,  -0.552887345195595,  -1.773997090857436,  -0.6549712334986287],
	    'CJ': [-0.7197954463771161,  -0.3855618389458571,  -0.5225478215844543,  0.010193007021379819,  -0.2898697024011009,  -1.865131533958604,  -1.4985025101392337,  -0.6209880454739807,  -1.7121484451524631,  -0.5946502637415154]
	}
	job_dict = {
	    'architecture': [-0.7937901908504912,  -0.3515023301323073,  -0.4554235835706492,  0.018568441679702063,  -0.3968237083739492,  -1.3308666785039271,  -1.6242769517092572,  -0.5504430612943971,  -1.885447745552018,  -0.6388167463616912], 
	    'IT': [-0.7696259816584736,  -0.3750560327936908,  -0.6220453215282576,  0.038524218054105,  -0.39442095530384713,  -1.544660045088205,  -1.5460983332577034,  -0.4308008828190042,  -1.8394535505010086,  -0.6759891813109313],
	    'management': [-0.5188299343485987,  -0.029901147197725186,  -0.6619645384923605,  -0.5445850626174967,  -0.5504030525920545,  -1.9072380753198996,  -1.74431572373821,  -0.24064155987741787,  -2.0314113808502823,  -0.46495878588644735],
	    'production': [-1.0853401959447126,  -0.7227053210850563,  -0.3608547010214966,  0.3813338308480395,  -0.576729031195717,  -0.4727332740131014,  -0.926939806428483,  -0.7580681852975474,  -1.486658160389664,  -0.759715089466218],
	    'sales': [-0.7978916757591541,  -0.31373308290366486,  -0.4780229416815024,  0.09685117511087538,  -0.42681929500149385,  -1.5038986238349443,  -1.4333001252748199,  -0.6346743462103275,  -1.9094620003216995,  -0.6192814575524809]
	}

	Compare(company_dict,company,SVCdecision(svc_from_pickle,"예측 텍스트입니다~ 가나다라마바사아자차카타파하"))
