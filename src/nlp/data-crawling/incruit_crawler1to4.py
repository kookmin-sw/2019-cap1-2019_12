import urllib.request as req
from urllib.parse import urlencode
from urllib.parse import urljoin
from bs4 import BeautifulSoup
import re
import sys
import io

sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding = 'utf-8')
sys.stderr = io.TextIOWrapper(sys.stderr.detach(), encoding = 'utf-8')


baseUrl = "http://people.incruit.com/resumeguide/pdslist.asp"

for i in range(4):#142
    values = {
        'page': i+1,
        'listseq':1,
        'pds1':1,
        'pds2':11,
        'pass':'Y'
    }
    params = urlencode(values)
    url = baseUrl + "?" + params
    #print("url=", url)

    res = req.urlopen(url).read()
    soup = BeautifulSoup(res,"html.parser")
    links = soup.find_all(href=re.compile(r"pdsview"))

    for link in links:
        href = link.attrs['href']
        href = "http://people.incruit.com/resumeguide/pdsview.asp?"+href[1:]

        res = req.urlopen(href).read()
        soup = BeautifulSoup(res,"html.parser")
        infos = soup.select('#detail_info > span.cont > div > p')

        title = soup.select('#content > div.bbsview_title > h2')[0].get_text().strip()
        title = title.replace("/","_")

        savePath="c:/"+title+".txt"
        saveFile = open(savePath,'w',encoding='utf8')

        for info in infos:
            saveFile.write(info.get_text().strip())
            saveFile.write("\n")

        resumes = soup.select('#detail_info > span.cont > p')
        for resume in resumes:
            saveFile.write(resume.get_text().strip())
            saveFile.write("\n")
        saveFile.close()

