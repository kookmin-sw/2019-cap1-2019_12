package com.capstone.server.dbtest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Resttest {
    public static void main(String[] args) throws IOException, ParseException {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(50000);
        factory.setConnectTimeout(3000);
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(5)
                .build();
        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        String url = "http://15.164.19.93:5000/";

        MultiValueMap<String, String> test = new LinkedMultiValueMap<String, String>();
        test.add("company", "CJ");
        test.add("q1", "[역량을 펼치고 싶습니다.]\n" +
                "끊임없는 자기 분석과 적성검사를 통해 저에게 적합한 직무를 고민해본 결과, 완벽한 분석을 통해 문제를 해결하는 분석력과 문제 해결력, 사소한 것 하나도 놓치지 않는 섬세함, 짧은 목표라도 세부적으로 잘라서 실행하는 치밀함 등이 생산기술 파트와 가장 적합하다는 결론이 나왔습니다. 또한 이는 생산 설비를 분석, 검토하고 적절한 곳에 배치하여 생산 시스템을 향상시키는 생산 기술 부문에서 일하기 위해 꼭 필요한 역량이라고 생각하기에 저의 역량을 십분 발휘하고 싶어 생산 기술 직무에 관심을 갖게 되었습니다.\n" +
                "\n" +
                "[저의 별명은 꼼꼼이입니다.]\n" +
                "항상 꼼꼼하게 메모를 하는 자세를 가지고 있습니다. 그리고 이러한 자세는 생산 공정에서 trouble이 발생했을 때 그 원인을 분석하고 해결해나가는 생산 기술의 업무에 큰 도움이 되어줄 것입니다.\n" +
                "저는 2년째 학원에서 수학 강사로 아르바이트를 해오고 있습니다. 학생들을 처음 맡았을 때부터 각 학생이 어떤 문제를 틀렸는지, 무슨 능력이 부족해 보이는지를 메모 해왔습니다. 덕분에 학생들이 어떤 능력이 향상되었고 어떤 능력에는 더 노력이 필요한지 쉽게 분석할 수 있었습니다. 또한, 이를 바탕으로 학생들마다 다른 문제를 풀도록 하였고 단계적으로 목표 점수를 설정하여 학생들 스스로 동기부여를 하게 만든 결과 모든 학생들의 성적이 150%이상씩 향상하여 인센티브까지 받게 되었습니다.\n" +
                "\n" +
                "[CJ제일제당과 함께 자라왔습니다.]\n" +
                "따끈한 밥 위에 스팸 한 조각. 제가 입맛이 없을 때마다 어머니께서 해주시던 반찬입니다. 아파서 앓고 난 다음 날, 시험 성적이 기대보다 낮게 나와 기분이 안 좋은 날이면 늘 어머니께서는 스팸을 구워 식탁에 올려주셨습니다. 스팸과 김치만 있으면 밥 한 그릇을 순식간에 비워내던 저에게 스팸은 어머니의 사랑과 추억이 담긴 식품입니다. 다른 누군가에게도 저와 같은 추억을 선물하고 싶어 CJ제일제당의 생산기술 부문에 지원 하게 되었습니다.\n" +
                "\n" +
                "[100명의 아이들을 이끌다!]\n" +
                "다큐멘터리에서 한 여학생이 울며 걷는 모습을 보았습니다. 울면서도 그녀가 걸을 수 있었던 것은 함께 걸으며 응원해주는 팀원들이 있기 때문이었고, 도착점에서 모두 얼싸안고 눈물을 흘리던 모습은 매우 감동적이었습니다. 이를 계기로 국토 대장정 진행에 관심을 가지게 되어 작년 8월 한 국토 대장정 프로그램에 참가 하였습니다.\n" +
                "제가 참가한 프로그램은 약 100명의 청소년들을 여러 중대로 나누고 각 중대를 한 명의 중대장이 맡아 인솔하는 대장정이기에 아이들의 안전에 각별히 신경 써야 했습니다. 그래서 무전기 사용 방법부터 아이들에게 안전한 휴식 공간을 마련해주는 방법까지 배워야 할 일들이 매우 많았습니다. 4월부터 7월까지 총 11번의 교육에서 지난 대장정에 참가했던 선배 기수 분들에게 이론을 배우고 최종 트레이닝 캠프에 참가하여 직접 행군을 하며 이론을 몸으로 익히며 대장정이 무사히 진행되도록 만반의 준비를 했습니다.\n" +
                "하지만 긴 시간의 연습이 무색해질 정도로 돌발 상황은 빈번히 발생했습니다. 넘어진 아이, 심지어 주먹다짐을 한 아이들도 있었습니다. 매일 밤 그런 아이들을 치료하고 상담해주고 다음 날의 일정을 준비하느라 3~4시간 정도로 짧게 쪽잠을 자면서도 긴 행군을 해낼 수 있었던 것은 다른 사람들과 ‘함께’했기 때문입니다. 지쳐있는 사람을 위해 뒤에서 밀어주고 함께 큰 소리로 노래를 부르면서 서로를 응원해주며 19박 20일이라는 긴 행군을 무사히 마쳤습니다. 그리고 대장정을 마치던 날, 아이들과 부둥켜안고 울면서 ‘우리는 나보다 강하다’라는 것을 저와 아이들의 마음에 다시 한 번 새겼습니다.\n" +
                "그리고 이는 제 인생에 최고의 경험이라고 해도 과언이 아닐 정도로 큰 변화를 가져다 주었습니다. 과제를 할 때에도 시험 공부를 할 때에도 혼자 자료를 찾고 공부하였던 저는 국토 대장정 이후 친구들과 서로 잘 하는 부분은 알려주고 모르는 부분은 배우며 ‘협력’의 중요성을 다시 한 번 깨달았습니다.");
        test.add("q2", "[최고 속의 최고가 되고 싶습니다.]\n" +
                "공정에서 일어나는 어떤 문제에도 유연하게 대처하는 최고의 engineer로 성장하고 싶습니다. 이를 위해서 다음 두 가지를 필히 지키겠습니다.\n" +
                "첫째, 스펀지 같은 흡수력으로 업무에 빠르게 적응하겠습니다. 생산 과정에서 사용되는 전문 용어들을 빠르게 익히고 한 번 배운 내용은 다시 배울 필요가 없도록 배운 모든 내용을 흡수하겠습니다. 이를 통해 제가 맡은 공정의 문제점을 분석하고 예방하여 안정화시켜 궁극적으로 불량률 10% 미만을 달성할 수 있도록 이바지 하겠습니다.\n" +
                "둘째, 매년 최고 효율을 달성할 수 있도록 노력하겠습니다.\n" +
                "기존 생산 공정의 미흡한 점을 찾아 개선하여 제품의 수율을 높이고 효율을 극대화 하는 것이 생산기술 파트의 가장 중요한 목표라고 생각합니다. 작년보단 올해에, 올해보단 내년에 더 큰 생산성을 창출하여 CJ제일제당이 대한민국 1위 식품 기업으로써의 입지를 더 단단히 하는데 기여하고 싶습니다.\",,,,\n" +
                "생산관리,CJ제일제당 011307,\"CJ제일제당은 우리나라 최대의 식품 기업입니다. 그래서 저는 생산엔지니어로서 적극적인 라인 증설과 사업 확장으로 글로벌 기업으로 성장하고 있는 CJ제일제당을 위해 안정적인 양산체제를 구축하여 생산성을 극대화하고 수익성 향상의 상대적인 우위를 차지하는 것에 일조하기 위해 지원하게 되었습니다. 이를 위해 저는 생산 엔지니어로서 자격이 있는 이유 3가지를 준비하였습니다.\n" +
                "\n" +
                "[생산 엔지니어로서 자격이 있는 이유 3가지]\n" +
                "첫째, 다수의 설계 프로젝트 진행 경험\n" +
                "팀 과제를 좋아하는 저에게는 설계 프로젝트는 굉장히 흥미로웠습니다. 이 중에 화공양론 설계가 가장 기억에 남습니다. 혼합된 원료에서 순수한 생산물을 얻어내는 공정을 설계하는 것이었습니다. 따라서 목표를 최고의 수율을 달성하면서 최적의 공정조건을 정하면서 최소의 공정설비를 이용하는 것으로 잡아 3가지를 함께 유기적으로 고려하며 설계를 하였습니다. 그 결과 최고점수를 획득하였고 저희 팀의 결과물은 후배들을 위해 설계 sample 자료로 활용되고 있습니다.\n" +
                "둘째, 조직 융화력\n" +
                "신입 엔지니어로서 가장 중요한 건 조직 내 빠른 융화라고 생각합니다. 학창시절부터 단체 생활을 통해 팀원으로서는 주인의식을, 리더로서는 책임감과 추진력 등을 키워왔기 때문에 그 누구보다 조직에 빠르게 융화될 자신이 있습니다.\n" +
                "셋째, 순간 판단력\n" +
                "생산 엔지니어로서 중요시되는 능력은 상황에 따라 임기응변 할 수 있는 빠른 순간 판단력이라고 생각합니다. 상황 조건에 따라 Steady State로 이루어지는 생산 프로세스에서 큰 금전적 손실을 줄이기 위해선 메뉴얼을 숙지함과 동시에 그 상황을 빠르게 인지하고, 최적의 판단을 빠르게 해야 그만큼 손실을 줄일 수 있습니다. 평소에도 팀 프로젝트를 할 때도 많은 아이디어와 방향성을 제시하는 것에 자신이 있었습니다.\n" +
                "\n" +
                "[두 번의 실패 후에 얻었기에 더욱 값진 성공!]\n" +
                "제가 겪었던 실패 후 다시 도전하여 성취감을 느낀 경험은 여러 가지가 있었지만, 그중에서 가장 큰 성취감과 행복감을 느낀 것은 대학교 입학을 이룬 것입니다. 수능은 사회라는 곳으로 떠나기 위해 배우는 4년간의 배움터이자 성인이 되면서 치르는 중요한 시험입니다. 하지만 그 부담감과 나 자신과의 기나긴 싸움. 그리고 결과적으로 보면 결국 원하는 대학에 진학하지 못하였습니다. 어린 나이에 처음 겪는 실패의 경험이라 더욱더 크게 느껴졌습니다.\n" +
                "실패의 경험을 바탕으로 저는 근시안적인 관점과 눈앞의 결과만을 바라보며 쏟는 노력만으로는 한계가 있다는 것을 알았습니다. 실패의 참 의미는 단순히 그 자체가 아닌, 똑같은 실패를 되풀이하지 않기 위해서 노력을 해야 한다는 교훈을 준다는 것입니다. 2번의 대입 실패 후, 막연히 학원에 다니기보단 1달 정도 나만의 시간을 가지며 곰곰이 생각해보았습니다. 그렇게 천천히 실패한 원인을 살펴보자, 지금까지 자기 주도의 공부를 해온 것이 아닌 학원의 주도하에 이루어진 공부를 해왔다는 것이 보였습니다. 실패의 원인을 알아낸 후, 스스로 공부에 대한 계획을 세우면서 학원의 일방적인 초고속 주입식 공부가 아니라 느리지만 스스로 알아낼 때까지 노력해보는 공부를 하였습니다.\n" +
                "그 결과, 염원하던 화학공학 전공을 할 수 있는 대학교에 입학할 수 있게 되었습니다. 고등학교 시절부터 정말 꿈꿔왔던 학문을 공부할 수 있을 것이란 기대감은 성취감을 더욱 크게 만들어 주었습니다. 이렇게 큰 성취감을 느낀 대에는 수차례의 실패 끝에 얻은 것이기 때문이라 생각합니다. 일찍이 몇 번의 실패를 맛 본 것이 지금 와서 생각해보면 현재의 저에게는 웬만한 힘든 일을 이겨낼 초석이 되었다고도 생각합니다.\"\"\"))");

        String obj;
        obj = restTemplate.postForObject(url, test ,String.class);

        System.out.println(obj);

     /*   String obj2 = obj.replaceAll("\\\\", "");

        System.out.println(obj);
        System.out.println(obj2);
        obj2 = obj2.substring(1, obj2.length()-2);

//        assert obj != null;
//        obj = obj.substring(1, obj.length()-2);
//        System.out.println(obj);

//        ObjectMapper mapper = new ObjectMapper();

        JSONObject feedback = (JSONObject) new JSONParser().parse(obj2);


        System.out.println(feedback);
*/

//
//        Map<String, Object > map = new HashMap<String, Object>();
//        map = mapper.readValue(obj, new TypeReference<Map<String, Object>>()  {});
//
//
//
//        System.out.println(obj);


/*
        // URL 에 있는 JSON String 을 Map으로 변환하기
        Map<String, Object> data = mapper.readValue(
                new URL("https://gturnquist-quoters.cfapps.io/api/random"),
                new TypeReference<Map<String,Object>>(){});

        // {type=success, value={id=9, quote=So easy it is to switch container in #springboot.}}
        System.out.println(data);

        System.out.println(data.get("value"));

        // Map을 JSON String 으로 변환
        System.out.println(mapper.writeValueAsString(data));

        // Map을 보기쉬운 JSON String 으로 변환
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
        */




    }
}

