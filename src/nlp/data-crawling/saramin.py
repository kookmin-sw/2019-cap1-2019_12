import json
from urllib.request import urlopen
from bs4 import BeautifulSoup

def parsing(doc_num):
  doc_num_str = str(doc_num)
  html = urlopen("http://www.saramin.co.kr/zf_user/public-recruit/coverletter?real_seq=" + doc_num_str)
  soup = BeautifulSoup(html, "html.parser")

  dic = {}

  company_name = soup.find("span", {'class' : 'tit_company_name'}).text
  bool_pass = soup.find("span", {'class': 'txt_recruit'}).text

  if '/' in bool_pass :
    bool_pass = bool_pass.replace('/' , ',')

  if '\t' in bool_pass:
    bool_pass = bool_pass.replace('\t' , ' ')

  if '/' in company_name :
    company_name = company_name.replace('/' , ',')


  if '합격자' in bool_pass:
    savePath = "/Users/hyeinkim/Desktop/capstone/자소서/jasoseo/saramin_parsing_합격자소서/"+"[" + company_name + "] " + bool_pass + ".json"
  else :
    savePath = "/Users/hyeinkim/Desktop/capstone/자소서/jasoseo/saramin_parsing/"+"[" + company_name + "] " + bool_pass + ".json"


  apply = soup.find("span", {'class' : 'tag_apply'}).text.split('|') # 지원분야

  dic["type"] = apply[1].lstrip()
  dic["company"] = company_name

  answer = ""

  for x in soup.find_all("div", {'class': 'box_ty3'}):
    # 하위요소 지우기
    x.button.decompose()
    x.find("div", {'class': 'txt_self_point'}).decompose()
    x.find("div", {'class': 'txt_byte'}).decompose()

    question = x.select('h3')[0].text  # 문항
    x.select('h3')[0].decompose()  # 공백 지우기 위해서 문항 지우기

    str_answer = x.text.strip()
    str_answer = str_answer.replace('\r\n', '')
    str_answer = str_answer.replace('\n', '')

    answer += question
    answer += str_answer

  dic["document"] = answer

  data = json.dumps(dic, ensure_ascii = False, indent="\t")
  with open(savePath, 'w', encoding="utf-8") as fp :
    fp.write(data)

for i in range(30000, 31000):
  try:
    parsing(i)
    print(i, "페이지 파싱 완료")

  except AttributeError:
    print(i, "AttributeError 정보 없음")