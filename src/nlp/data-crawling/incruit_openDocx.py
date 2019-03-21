import sys
import io
import re

import os
import glob

from docx import Document

sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding = 'utf-8')
sys.stderr = io.TextIOWrapper(sys.stderr.detach(), encoding = 'utf-8')

path = 'C:\\인크루트_합격자소서'
#for filename in os.listdir(path):
for filename in glob.glob(os.path.join(path,'*.docx')):
    print(filename)
    document = Document(filename)

    filename = filename.split("\\")[2]
    filename = filename.split(".docx")[0]
    print(filename)
    savePath="C:\\인크루트_합격자소서_DocOpen\\"+filename+".txt"
    saveFile = open(savePath,'w',encoding='utf8')

    for para in document.paragraphs:
        #print(para.text)
        saveFile.write(para.text)
        saveFile.write("\n")

    saveFile.close()
