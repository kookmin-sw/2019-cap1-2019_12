import json
from urllib.parse import quote
import re
import requests
from urllib.request import urlopen
from bs4 import BeautifulSoup

keyword_1 = ["소통", "대화", "교류"] #소통
keyword_2 = ["팀워크", "협력", "협동"] #팀워크
keyword_3 = ["도전", "모험", "용기"] #도전
keyword_4 = ["능동", "열정", "적극"] #능동
keyword_5 = ["성실", "근면","충실"] #성실
keyword_6 = ["정직","진실", "솔직"] #정직
keyword_7 = ["인내심", "끈기", "근성"] #인내심
keyword_8 = ["창의", "창조","독창"] #창의
keyword_9 = ["문화", "국제", "외국어"] #글로벌 역량
keyword_10 = ["책임","의무", "자기주도"] #주인의식

keyword_dic = {'1' : keyword_1 , '2' : keyword_2, '3' : keyword_3, '4' : keyword_4, '5' : keyword_5, '6' : keyword_6 , '7' : keyword_7 , "8" : keyword_8 , "9" : keyword_9 , "10" : keyword_10}

def parsing(num):
  tmp = str(num)
  n = len(keyword_dic[tmp])

  for i in range (0, n):
    savePath = "/Users/hyeinkim/Desktop/capstone/keyword/" + keyword_dic[tmp][i] + ".txt"
    decode_keyword = quote(keyword_dic[tmp][i])
    sentence_num = 0
    page_num = 1
    data_str = ""

    while page_num < 81:
      page_num_str = str(page_num)
      #http://search.daum.net/search?nil_suggest=btn&w=news&DA=PGD&cluster=y&q=%EA%B8%80%EB%A1%9C%EB%B2%8C+%EC%97%AD%EB%9F%89&p=2
      url = "http://search.daum.net/search?nil_suggest=btn&w=news&DA=PGD&cluster=y&q=" + decode_keyword + "&p=" + page_num_str
      temp_result = requests.get(url)
      soup = BeautifulSoup(temp_result.text, "html.parser")
      list = soup.select('p.f_eb.desc') # 각 페이의 뉴스 리스트들
      for x in list :
        split_str = x.text.split(".")
        for y in split_str :
          if keyword_dic[tmp][i] in y :
            sentence_num = sentence_num + 1
            data_str = data_str + y +"."
            print(sentence_num, y)

      page_num = page_num + 1

    with open(savePath, 'w', encoding="utf-8") as fp:
      p = re.compile('\(.*?\)')  # 괄호 안의 쓸데없는 단어 지우기
      p2 = re.compile('\[.*?\]')
      p3 = re.compile('\【.*?\】')
      real_text = re.sub(p, "", data_str)
      real_text = re.sub(p2, "", real_text)
      real_text = re.sub(p3, "", real_text)

      print(real_text)

      fp.write(real_text)

    print("—————")


for i in range(0, 11):
  parsing(i)