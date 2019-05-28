import urllib.request as req
from urllib.parse import urlencode
from urllib.parse import urljoin
import webbrowser

from bs4 import BeautifulSoup
import docx

import re
import sys
import io

sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding = 'utf-8')
sys.stderr = io.TextIOWrapper(sys.stderr.detach(), encoding = 'utf-8')


baseUrl = "http://people.incruit.com/resumeguide/pdslist.asp"


for i in range(13,142):#142
    values = {
        'page': i+1,
        'listseq':1,
        'pds1':1,
        'pds2':11,
        'pass':'Y'
    }
    params = urlencode(values)
    url = baseUrl + "?" + params
    #print("################################################################")
    #print("url=", url)

    res = req.urlopen(url).read()
    soup = BeautifulSoup(res,"html.parser")
    links = soup.find_all(href=re.compile(r"pdsview"))

    for link in links:
        href = link.attrs['href']
        href = "http://people.incruit.com/resumeguide/pdsview.asp?"+href[1:]

        res = req.urlopen(href).read()
        soup = BeautifulSoup(res,"html.parser")
        download = soup.find(class_='rsmview_file')

        if download == None:
            continue

        download = download.select('p.download > button')
        download = download[0].get('onclick')
        download = (download.split(";"))[1]
        download = download.split("','")

        filePath = download[1]
        fileName = download[2][:-2]
        print(fileName)

        fileName = fileName.encode('unicode-escape').decode()
        fileName = fileName.replace('\\','%')

        fileURL = "http://file.incruit.com/G_Common/Library/FileDownLoader.asp?FilePath="+filePath+"&FileName="+fileName

        webbrowser.open(fileURL)
