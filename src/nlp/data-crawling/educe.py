import json
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException

'''
경영
 - 사무.총무.법무.특허 / 577 / scrollPos1013
 - 경리.회계.세무.결산 / 81 / scrollPos1015
 - 일반사무.사무지원.문서작성 / 54 / scrollPos1019

생산 7837 / scrollPos1006
 - 기술.연구개발.R&D / 4527
 - 설계.CAD·CAM / 601
 - 생산관리.공정관리.품질관리 / 2192
 - 생산.기능.조립.제조.포장 / 154
 - 기계.금속.금형.용접.도금 / 103
 - 전기기술.제어.전자.회로 / 206
 - A/S.정비.설치.수리 / 54
 
IT 1026 / scrollPos1008
 - 웹기획.웹마케팅.PM / 24
 - 콘텐츠.사이트운영 / 9
 - 웹디자인.HTML코딩 / 4
 - 웹프로그래밍 / 39
 - 응용프로그래밍 / 252
 - 시스템프로그래밍 / 105
 - 서버.SE.보안.네트워크.LAN / 199
 - 시스템분석.설계.DB / 166
 - 하드웨어설계.개발.유지보수 / 33
 - 게임기획.개발.운영(GM) / 19
 - 모바일.유무선.통신 / 176
 
영업 2202 / scrollPos1005
 - 제조.건설.유통.서비스영업
 - 금융.보험영업
 - 기술영업(IT.솔루션) 
 - 광고영업
 - 영업관리.영업기획.영업지원
 - 판매.매장관리.캐셔
 - TM.아웃바운드.해피콜
 - CS.인바운드.고객지원.상담
 - 홍보상담·단순홍보·고객관리
 - 교육상담.학원운영관리=
  
건축 1526 / scrollPos1007
 - 건축설계.인테리어.시공
 - 토목.조경.측량.구조
 - 시설.환경.플랜트
 - 전기.소방.설비
 - 건설사무.감리.안전.검사
 
'''

#로그인 셋팅
driver = webdriver.Chrome("/Users/hyeinkim/desktop/capstone/chromedriver")

driver.get("https://career.kookmin.ac.kr/")

el = driver.find_element_by_xpath(".//*[@id='user_id']")
el.send_keys("20153169")

el = driver.find_element_by_xpath(".//*[@id='user_pw']")
el.send_keys("패스워드")

el = driver.find_element_by_xpath(".//*[@id='btnLogin']")
el.click()

el = driver.find_element_by_xpath(".//*[@id='gnbtopmenu4']")
el.click()

el = driver.find_element_by_xpath('//*[@id="leftmenu_leftmenu"]/ul/li[3]')
el.click()


driver.get("http://u.educe.co.kr/jobkookmin")
driver.refresh()

jasoseo_list = []  # type , company, document


def page_number_parsing(category_list): #page number 정보 수집

    page_num = int((category_list["page_number"] / 15) + 1)
    current_page_num = 1

    for i in range (1, page_num):
        #link = 'http://u.educe.co.kr/jobkookmin/?mod=passReport&m_idx=19&gMode=1&mno=' + category_list["id"] + '#category&page='+ str(i) + '#list'
        link = 'http://u.educe.co.kr/jobkookmin/?mod=passReport&m_idx=19&gMode=1&bno=' + category_list["id"] + '&chk_mno=Y&page=' + str(i) + '#list'
        driver.get(link)

        for j in range (1, 16) :
            jasoseo_dic = {}

            el = driver.find_element_by_xpath('//*[@class="contents"]//*[@class="bbs_ltype"]/tbody/tr['+str(j)+']/td/a').get_attribute('onclick')
            page_num_info = el.replace("'", "")
            page_num_info = page_num_info.replace("paperView","")
            page_num_info = page_num_info.replace(";","")
            page_num_info = page_num_info.replace("(", "")
            page_num_info = page_num_info.replace(")", "")
            page_num_info = page_num_info.split(",")
            #page number
            
            el = driver.find_element_by_xpath( '//*[@class="contents"]//*[@class="bbs_ltype"]/tbody/tr[' + str(j) + ']/td/a/span').text
            company_name = el
            #company name

            type_name = driver.find_element_by_xpath('//*[@class="contents"]//*[@class="bbs_ltype"]/tbody/tr[' + str(j) + ']/td[3]').text

            #type name

            jasoseo_dic["type"] = type_name
            jasoseo_dic["company"] = company_name
            jasoseo_dic["document"] = page_num_info[1]

            jasoseo_list.append(jasoseo_dic)

            #driver.get("http://u.educe.co.kr/module/paperContents/?content_type="+ str(page_num_info_1) +"&product_no="+ str(page_num_info_2)+"&m_idx=" + page_num_info_3)


def jasoseo_parsing(jasoseo_list):

    for i in range (0, len(jasoseo_list)):
        driver.get("http://u.educe.co.kr/module/paperContents/?content_type=5&product_no="+jasoseo_list[i]["document"]+"&m_idx=19")
        content = driver.find_element_by_xpath('//*[@class="content"]').text
        jasoseo_list[i]["document"] = content

    return jasoseo_list


def main(jasoseo_list):

    #{"category": "영업", "id": "1005", "page_number": 2202}
    #{"category": "생산", "id": "1006", "page_number": 7837}
    #{"category": "IT", "id": "1008", "page_number": 1026}
    #{"category": "건축", "id": "1007", "page_number": 1526}

    category_list = [{"category": "건축", "id": "1007", "page_number": 1526},
                     {"category": "영업", "id": "1005", "page_number": 2202},
                     {"category": "생산", "id": "1006", "page_number": 7837},
                     {"category": "IT", "id": "1008", "page_number": 1026}]

    for i in range (0, len(category_list)):

        try:
            page_number_parsing(category_list[i])
            jasoseo_parsing(jasoseo_list)


        except NoSuchElementException:
            print("Error")
            continue

        savePath = "/Users/hyeinkim/Desktop/capstone/자소서/educe/educe_" + category_list[i]["category"] + "_" + str(len(jasoseo_list))+ ".json"

        data = json.dumps(jasoseo_list, ensure_ascii = False, indent="\t") #파일 쓰기
        with open(savePath, 'w', encoding="utf-8") as fp :
            fp.write(data)

        print(len(jasoseo_list))
        jasoseo_list = [] #초기화




main(jasoseo_list)
