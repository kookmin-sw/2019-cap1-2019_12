import json
from collections import OrderedDict
import glob
import os

path = 'C:\\resume'
for filename in glob.glob(os.path.join(path, '*.txt')):
    f = open(filename, 'r', encoding="utf-8")

    lines = f.readlines()
    if len(lines) <= 0:
        continue

    #직무
    type_str = lines[1].split(":")[1].replace("\n", "")
    print(type_str)

    #회사
    company_str = lines[0].split("]")[0].split("[")[1].replace("\n", "")
    print(company_str)

    #자기소개서 본문
    document_str = ""
    for i in range(2, len(lines)):
        if lines[i] == "\n":
            continue
        document_str += lines[i]

    document_str.replace('\r', '').replace('\n', '').replace('\\n', '')
    print(document_str)

    file_data = OrderedDict()
    file_data["type"] = type_str
    file_data["company"] = company_str
    file_data["document"] = document_str.replace("\n", "")

    filename = filename.split(".txt")[0]
    with open(filename+".json", 'w', encoding="utf-8") as make_file:
        json.dump(file_data, make_file, ensure_ascii=False, indent="\t")
