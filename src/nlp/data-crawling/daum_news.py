import json
from urllib.parse import quote
import re
import requests
from urllib.request import urlopen
from bs4 import BeautifulSoup
import csv

'''
1 글로벌역량 / 문화(문화상품권) 국제적 외국어
2 능동 / 능동적 열정적 적극적
3 도전 / 도전적(무한도전) 모험 담력
4 성실 / 성실한 근면한 충실한
5 소통 / 소통 대화 교류
6 인내심 / 인내심 끈기 근성
7 정직 / 정직한 진실 솔직
8 주인의식 / 책임 의무 자기주도
9 창의 / 창의적 창조 독창
10 팀워크 / 팀워크 협력 협동

'''
keyword_1 = ["글로벌역량","문화", "국제적", "외국어"] #글로벌 역량
keyword_2 = ["능동","능동적", "열정적", "적극적"] #능동
keyword_3 = ["도전","도전적", "모험", "담력"] #도전
keyword_4 = ["성실","성실한", "근면한","충실한"] #성실
keyword_5 = ["소통","소통", "대화", "교류"] #소통 대화 교류
keyword_6 = ["인내심","인내심", "끈기", "근성"] #인내심
keyword_7 = ["정직","정직한","진실", "솔직"] #정직
keyword_8 = ["주인의식","책임","의무", "자기주도"] #주인의식
keyword_9 = ["창의","창의", "창조","독창"] #창의
keyword_10 = ["팀워크","팀워크", "협력", "협동"] #팀워크


keyword_dic = {'1' : keyword_1 , '2' : keyword_2, '3' : keyword_3, '4' : keyword_4, '5' : keyword_5, '6' : keyword_6 , '7' : keyword_7 , "8" : keyword_8 , "9" : keyword_9 , "10" : keyword_10}

def parsing(num):
  tmp = str(num)
  n = len(keyword_dic[tmp])
  sentence_list = []

  savePath = "/Users/hyeinkim/Desktop/capstone/keyword/" + keyword_dic[tmp][0] + ".csv"

  for i in range (1, n):


    decode_keyword = quote(keyword_dic[tmp][i])
    sentence_num = 0
    page_num = 1
    data_str = ""

    while page_num < 81:
      page_num_str = str(page_num)
      #http://search.daum.net/search?nil_suggest=btn&w=news&DA=PGD&cluster=y&q=%EA%B8%80%EB%A1%9C%EB%B2%8C+%EC%97%AD%EB%9F%89&p=2
      url = "http://search.daum.net/search?nil_suggest=btn&w=news&DA=PGD&cluster=n&q=" + decode_keyword + "&p=" + page_num_str
      temp_result = requests.get(url)
      soup = BeautifulSoup(temp_result.text, "html.parser")
      list = soup.select('p.f_eb.desc') # 각 페이의 뉴스 리스트들

      real_text_list = []

      for x in list :
        split_str = x.text.split(".")
        for y in split_str :
          if keyword_dic[tmp][i] in y :
            sentence_num = sentence_num + 1
            data_str = data_str + y +"."

            p = re.compile('\(.*?\)')  # 괄호 안의 쓸데없는 단어 지우기
            p2 = re.compile('\[.*?\]')
            p3 = re.compile('\【.*?\】')
            real_text = re.sub(p, "", y)
            real_text = re.sub(p2, "", real_text)
            real_text = re.sub(p3, "", real_text)

            print(sentence_num, y)
            real_text_list = [tmp, real_text]
            sentence_list.append(real_text_list)

      page_num = page_num + 1

  csvfile = open(savePath, 'w', encoding="utf-8", newline="")
  csvwriter = csv.writer(csvfile)

  print(len(sentence_list))

  csvwriter.writerow(["label","sentence"])
  for row in sentence_list:
    csvwriter.writerow(row)

  csvfile.close()
  sentence_list = []


for i in range(1, 11):
  parsing(i)