
import os
import json


path_dir = '/Users/hyeinkim/Desktop/capstone/자소서/jasoseoldotcom'

file_list = os.listdir(path_dir)


def txt_to_json(file_num):
    split_file_name = file_list[file_num].split('_')
    index = split_file_name[0]
    company = split_file_name[1]
    type = split_file_name[2][:-4]


    file = open(path_dir+"/"+file_list[file_num])

    file_text = file.read()

    file_text = file_text.split('\n\n\n\n')

    file_text[0] = ''
    file_text[1] = ''
    file_text[2] = ''

    file_text_str = ''

    for text in file_text :
        file_text_str += text.replace('\n','')

    file_text_str  = file_text_str.replace('(주)스펙업애드에서 합격자기소개서를 제공받았습니다.','')

    dic = {}

    dic['type'] = type
    dic['company'] = company
    dic['document'] = file_text_str

    savePath = '/Users/hyeinkim/Desktop/capstone/자소서/jasoseoldotcom_json/' + index + '_' + company + '_' + type + '.json'

    data = json.dumps(dic, ensure_ascii = False, indent="\t")
    with open(savePath, 'w', encoding="utf-8") as fp :
        fp.write(data)

    print(dic)


for i in range(0 , len(file_list)):
    #print(len(file_list))
    txt_to_json(i)

