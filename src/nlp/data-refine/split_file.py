# 삼성 현대 엘지 SK CJ
# 영업 생산 IT 경영지원관리 건축

import json

with open('/Users/hyeinkim/Desktop/capstone-git/refine_docs.json') as data_file:
    data = json.load(data_file)

savePath = '/Users/hyeinkim/Desktop/capstone-git/'

# -----------------기업별
list_Samsung = []
list_Hyundai = []
list_LG = []
list_SK = []
list_CJ = []
# ----------------직무별
list_Sales = []
list_Production = []
list_IT = []
list_Management = []
list_Construct = []

type = [list_Sales, list_Production, list_IT, list_Management, list_Construct]
company = [list_Samsung, list_Hyundai, list_LG, list_SK, list_CJ]

type_name = ['영업', '생산', 'IT', '경영지원관리', '건축']
company_name = ['삼성', '현대', 'LG', 'SK', 'CJ']

for i in range(len(data)):
    dic = {}
    dic['type'] = data[i][0]
    dic['company'] = data[i][1]
    dic['document'] = data[i][2]

    if (data[i][0] == '영업'):
        list_Sales.append(dic)

    elif (data[i][0] == '생산'):
        list_Production.append(dic)

    elif (data[i][0] == 'IT'):
        list_IT.append(dic)

    elif (data[i][0] == '경영지원관리'):
        list_Management.append(dic)

    elif (data[i][0] == '건축'):
        list_Construct.append(dic)

for i in range(5):
    split_dic = json.dumps(type[i], ensure_ascii=False, indent="\t")
    with open(savePath + type_name[i] + '.json', 'w', encoding="utf-8") as fp:
        print(type_name[i])
        fp.write(split_dic)

# -----------------
for i in range(len(data)):
    dic = {}
    dic['type'] = data[i][0]
    dic['company'] = data[i][1]
    dic['document'] = data[i][2]

    if (data[i][1] == '삼성'):
        list_Samsung.append(dic)

    elif (data[i][1] == '현대'):
        list_Hyundai.append(dic)

    elif (data[i][1] == 'LG'):
        list_LG.append(dic)

    elif (data[i][1] == 'SK'):
        list_SK.append(dic)

    elif (data[i][1] == 'CJ'):
        list_CJ.append(dic)

for i in range(5):
    split_dic = json.dumps(company[i], ensure_ascii=False, indent="\t")
    with open(savePath + company_name[i] + '.json', 'w', encoding="utf-8") as fp:
        print(company_name[i])
        fp.write(split_dic)

# print(list_Sales)

for i in range(5):
    print(type_name[i] + ":", len(type[i]))

for i in range(5):
    print(company_name[i] + ":", len(company[i]))
