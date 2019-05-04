import urllib.request as req
from urllib.parse import urlencode
from urllib.parse import urljoin

from bs4 import BeautifulSoup

import re
import sys
import io

sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding = 'utf-8')
sys.stderr = io.TextIOWrapper(sys.stderr.detach(), encoding = 'utf-8')

baseUrl = "https://www.career.go.kr/cnet/front/base/job/jobInterview_new/jobSpecialList_new.do"

for i in range(1,47):
    url = baseUrl+"?pageIndex="+str(i)

    res = req.urlopen(url).read()
    soup = BeautifulSoup(res,"html.parser")
    links = soup.select('.interview_name > a')
    print(links)

    for link in links:
        interview = "https://www.career.go.kr"+link['href']
        #title = links[j]['title'].replace(" ", "_")
        title = link.get_text().replace("\n","").replace(" ","").replace("\xa0","").replace("/","").replace(":","_")
        print("title==",title)

        savePath="c:/interview/"+title+".txt"
        saveFile = open(savePath,'w',encoding='utf8')

        res = req.urlopen(interview).read()
        soup = BeautifulSoup(res,"html.parser")
        qnas = soup.select(".bubble")

        if len(qnas)<=0:
            qnas = soup.select(".article_para")
            saveFile.write(qnas[0].get_text())
            print(qnas[0].get_text())
        else:
            for qna in qnas:
                saveFile.write(qna.get_text())
                print(qna.get_text())
        saveFile.close()
