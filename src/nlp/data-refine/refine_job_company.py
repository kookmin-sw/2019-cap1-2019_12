import json
import os
import glob
import xlrd, xlwt

wb = xlrd.open_workbook('dataset/joblist.xlsx')
# wb = xlrd.open_workbook('dataset/company_list.xlsx')

sh = wb.sheet_by_index(0)
d = {}

for i in range(2701):
    cell_value_class = sh.cell(i,1).value
    cell_value_id = sh.cell(i,0).value
    d[cell_value_id] = cell_value_class

with open('dataset/train_docs.json') as json_file:
    json_data = json.load(json_file)
num = 0

for i in json_data:
    if (i[0] == ''):
        i[0] = 'anonym'
    print(i[0], "-> ", end='')
    num+=1
    print(d[i[0]])
    i[0] = d[i[0]]
print(num)


