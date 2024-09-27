package model.common;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import model.board.BoardDTO;
import model.member.MemberDAO;
import model.member.MemberDTO;
import model.product.ProductDTO;


public class CrawlingSelenium {
	//WebDriver 인스턴스를 정의
	private static WebDriver driver;

	public CrawlingSelenium() { // 셀레니움 옵션 설정
		// 크롬 옵션 설정
		ChromeOptions options = new ChromeOptions();
		//헤드리스 모드 추가 (코드 실행시 크롬창이 뜨지않게 함)
		options.addArguments("--headless");
		//팝업창 제거 옵션 추가
		options.addArguments("--disable-popup-blocking");
		//GPU 가속 비활성화
		options.addArguments("--disable-gpu");
		//샌드박스 비활성화
		options.addArguments("--no-sandbox");

		// 옵션설정한 ChromeDriver 인스턴스 생성
		driver = new ChromeDriver(options);
	}

	public void CrawlingSeleniumDown() {// 웹 드라이버 종료
		// WebDriver를 종료 메서드
		if (driver != null) {
			// 드라이버 브라우저 연결 끊기
			driver.quit();
			// 브라우처 창 닫기
			//driver.close()
		}
	}
	public WebDriver getDriver() { // 웹 드라이버 사용
		// WebDriver 사용
		return driver;
	}
	/*	
	private static void start() {

		//WebDriver 객체 생성
		//driver = new ChromeDriver();

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성
		//JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// driver(웹 드라이버) 인스턴스를 JavascriptExecutor로 변환

		// 주소 설정(메인 페이지)
		String url = "https://www.moolban.com/";
		// 웹 페이지 접속
		driver.get(url);
		System.out.println("77 로그 url :["+url+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)
		// manage() : WebDriver.Options 인터페이스 반환 > 브라우저 관리 메서드 제공
		// window() : WebDriver.Options 인터페이스 메서드/ 브라우저 창 제어 하는 WebDriver.Window 객체 반환
		// maximize() : WebDriver.Window 객체 메서드/ 브라우저 창을 최대화(최대 크기)


	}
	 */
	//-----------------------------------------------------------------------
	public static ArrayList<ProductDTO> makeSampleProductSeaBoat() { // 바다 낚시배
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductSeaBoat 시작");

		//상품 샘플을 저장할 ArrayList 생성
		ArrayList<ProductDTO> product = new ArrayList<>(); //상품 저장 배열

		//WebDriver 객체 생성
		//driver = new ChromeDriver();

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성
		//JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// driver(웹 드라이버) 인스턴스를 JavascriptExecutor로 변환

		// 주소 설정(메인 페이지)
		String url = "https://www.moolban.com/";
		// 웹 페이지 접속
		driver.get(url);
		System.out.println("102 로그 url :["+url+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)
		// manage() : WebDriver.Options 인터페이스 반환 > 브라우저 관리 메서드 제공
		// window() : WebDriver.Options 인터페이스 메서드/ 브라우저 창 제어 하는 WebDriver.Window 객체 반환
		// maximize() : WebDriver.Window 객체 메서드/ 브라우저 창을 최대화(최대 크기)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// 현재 ChromeDriver의 대기 시간 20초를 부여
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// WebDriverWait : Selenium 클래스/ 조건이 만족될 때 까지 브라우저를 기다리게 함
		// driver를 사용 > 웹 페이지 제어
		// Duration : java 클래스/ 시간 설정()
		// ofSeconds : Duration 클래스의 정적 메서드/ 시간 표현
		//	>>seconds : 초 단위 정수값

		// 쿠키 팝업 닫기
		//"body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close"
		WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close")));
		// WebElement : 요소를 객체로 반환
		// wait : WebDriverWait 객체/ 조건 만족 까지 기다림(20초)
		// wait.until() : 메서드 지정 조건 만족 까지 기다림/ css 선택자로 지정된 요소 페이지(가시성이 있을 때 까지 기다림)
		// ExpectedConditions : Selenium WebDriver에 다양한 조건 정의 메서드/ 웹 페이지 대기
		// visibilityOfElementLocated : 가시성(visible) 상태일 때까지 대기 조건/ 조건 충족 때까지 WebDriverWait대기
		// By.cssSelector : Selenium WebDriver에서 웹 페이지의 요소 찾기 위한 CSS선택자 사용하는 방법 제공

		// 쿠키 클릭
		cookie.click();
		//click() : WebElement 클래스의 메서드/ 클릭 자동 수행

		// 상품 카테고리
		System.out.println("135 로그: 상품 카테고리 클릭 시작");
		// 바다 페이지
		//"div.wrap_area > div.header_area > header > section > div > a:nth-child(2)"
		WebElement product_sea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.header_area > header > section > div > a:nth-child(2)")));
		// 클릭
		product_sea.click();

		// 상품 요소 저장 변수
		WebElement product_element = null;
		// 상품 링크를 저장할 변수
		String href="";

		for(int i = 2; i < 8; i++) {           
			try {
				for(int a=2; a<=i;a++) {
					try {
						// 상품 요소 찾기
						// pruduct_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#category_list > a:nth-child("+a+").list_box_area.list_ad_box_area.list_ad_box_area4")));                  
						//"#category_list > a:nth-child("+a+")"
						product_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#category_list > a:nth-child("+a+")")));
						href = product_element.getAttribute("href"); // 링크 href 추출

						// 상품 링크 확인
						System.out.println("158 로그 상품 링크 :["+href+"]");

					}catch (Exception e) {
						System.out.println("161 로그 : 상품 요소를 찾지 못함.");
						continue;	// 요소를 찾지 못하면 계속 진행
					}
					//상품 요소 확인
					System.out.println("165 상품 요소 :["+product_element+"]");
					// 상품 요소가 화면에 보이도록 스크롤
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", product_element);
					// jsExecutor : JavascriptExecutor 인터페이스의 인스턴스로, JavaScript 코드를 실행할 수 있는 WebDriver 객체
					//  > DOM에 직접 접근하여 JavaScript를 실행
					// executeScript() :  JavaScript 코드를 실행
					// arguments[0] : arguments 배열의 첫 번째 요소를 참조(product_element 배열의 첫 번째 요소로 전달)
					// scrollIntoView(true) : 요소가 화면에 표시되도록 스크롤
					// > true: 요소가 화면의 상단에 맞추어 스크롤/ false: 요소가 화면의 하단에 맞추어 스크롤
				}

				// 상품 페이지로 이동
				System.out.println("177 로그: 상품 페이지 이동");
				driver.get(href);
				//웹 브라우저를 특정 URL(href)로 이동

				// 객체 저장 
				ProductDTO productDTO = new ProductDTO();

				//남은 인원 요소
				//#reserve_section > a > div > span
				try {
					List<WebElement> people_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a > div > span")));
					System.out.println("188 로그:"+people_Element +" : [" + people_Element.size()+"]");
					//Selenium WebDriver를 사용 
					//웹 페이지에서 특정 CSS 선택자와 일치하는 모든 요소를 찾고, 
					//이 요소들이 가시성 상태가 될 때까지 기다리는 기능을 수행

					// 남은 인원 객체에 저장
					System.out.println("194 로그: ["+people_Element.get(0).getText()+"]");
					productDTO.setProduct_cnt(Integer.parseInt(people_Element.get(0).getText().replace("남은수", "").replace("명", "")));;
					// Integer.parseInt : 문자열을 정수로 변환 메서드
					// get(0) : 리스트 첫 번째 요소
					// getText() : 요소의 택스트를 가져옴
					// replace() : 문자열 특정 부분을 다른 문자열로 교체 메서드
				}
				catch (Exception e) {
					System.out.println("201 로그 : 남은 인원 요소를 찾지 못함.");
					productDTO.setProduct_cnt(15);
				}

				// 가격
				//#reserve_section > a > div > div.reserve_price > p
				try {
					List<WebElement> price_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a > div > div.reserve_price > p")));
					System.out.println("210 로그:"+price_Element + " : [" + price_Element.size()+"]");
					// 가격 객체에 저장
					System.out.println("212 로그: ["+price_Element.get(0).getText()+"]");
					productDTO.setProduct_price(Integer.parseInt(price_Element.get(0).getText().replace(",", "").replace(" 원", "")));;
				}
				catch (Exception e) {
					System.out.println("216 로그 : 가격 요소를 찾지 못함.");
					productDTO.setProduct_price(10000);
				}

				// 상품명
				//div.profile_info > dl > dt > h1
				try {
					List<WebElement> name_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.profile_info > dl > dt > h1")));
					System.out.println("224 로그:"+name_Element +" : [" + name_Element.size()+"]");

					// 상품명 객체에 저장
					System.out.println("227 로그: ["+name_Element.get(0).getText()+"]");
					productDTO.setProduct_name(name_Element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("231 로그 : 상품명 요소를 찾지 못함.");
					productDTO.setProduct_name("고래밥");
				}

				// 이용정보 클릭
				//"body > div.wrap_area > div.view_area.container > div.view_tab_area > section > ul > li:nth-child(3)"
				WebElement pruduct_details = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.view_tab_area > section > ul > li:nth-child(3)")));
				// 클릭
				pruduct_details.click();

				// 상품 주소
				//"#view_info > section:nth-child(1) > div.view_box.view_map > div.map_link > h2 > a"
				try {
					List<WebElement> address_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#view_info > section:nth-child(1) > div.view_box.view_map > div.map_link > h2 > a")));
					System.out.println("245 로그: "+address_Element + " : [" + address_Element.size()+"]");
					// 상품 주소 객체에 저장
					System.out.println("247 로그: ["+address_Element.get(0).getText()+"]");
					productDTO.setProduct_address(address_Element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("251 로그 : 상품 주소 요소를 찾지 못함.");
					productDTO.setProduct_address("강원도 동해시 목포항");
				}

				// 상품 내용
				//"#view_info > section:nth-child(1) > div:nth-child(2) > div > h2"
				try {
					List<WebElement> details_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#view_info > section:nth-child(1) > div:nth-child(2) > div > h2")));
					System.out.println("259 로그: "+details_Element + " : [" + details_Element.size()+"]");

					// 상품 내용 객체에 저장
					System.out.println("262 로그: ["+details_Element.get(0).getText()+"]");
					productDTO.setProduct_details(details_Element.get(0).getText());}
				catch (Exception e) {
					System.out.println("265 로그 : 상품 내용 요소를 찾지 못함.");
					productDTO.setProduct_details("즐거운 낚시~!");
				}

				// 이미지를 포함하는 요소를 찾기. (이미지 컨테이너의 CSS 선택자를 사용)
				By product_imgs = By.cssSelector("section > div > div.profile_slide > div.profile_list.company_image_list.swiper-wrapper");

				// 이미지 요소를 찾기 위한 CSS 선택자
				By image_Selector = By.cssSelector("img");

				// 이미지 컨테이너가 보일 때까지 기다림.
				WebElement image_Container = wait.until(ExpectedConditions.visibilityOfElementLocated(product_imgs));

				// 이미지 요소를 찾기.
				List<WebElement> product_images = image_Container.findElements(image_Selector);

				if (product_images.isEmpty()) { // 이미지 요소가 없을 시.
					System.out.println("282 로그: 이미지가 없음");
				} 
				else { // 이미지 요소가 있을 시
					System.out.println("285 로그: 이미지 개수 = [" + product_images.size()+"]");
					// 이미지 URL을 저장할 리스트 생성
					List<String> imageUrls = new ArrayList<>();
					// 각 이미지 요소의 src 속성 출력
					for (WebElement imgElement : product_images) {
						String img_src = imgElement.getAttribute("src");
						System.out.println("237 로그: 이미지 URL = [" + img_src+"]");
						imageUrls.add(img_src); // 리스트에 이미지 URL 추가
						productDTO.setUrl(imageUrls);
					}
				}
				// 결과 추출
				//System.out.println("297 결과"+product);
				product.add(productDTO); //배열리스트에 객체 추가
				driver.navigate().back();
				// navigate() : WebDriver의 메서드/ 페이지 이동, 뒤로 가기, 앞으로 가기, 새로 고침과 같은 탐색 작업을 수행
				// back() : Navigation 인터페이스의 메서드/ 뒤로 가기
				// 브라우저의 이전 페이지로 이동
			}catch (Exception e) {
				// 오류가 발생시 다음으로 넘어감
				System.err.println("313 로그 오류 발생: [" +i+"]");
				e.printStackTrace();
			}
		}
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductSeaBoat 종료");

		// 드라이버 종료
		//driver.quit();
		// quit() : WebDriver 클래스의 메서드/ 현재의 WebDriver 세션을 종료
		return product;			
	}
	//-----------------------------------------------------------------------
	public static ArrayList<ProductDTO> makeSampleProductSeaFishing() { // 바다 낚시터
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductSeaFishing 시작");

		// 상품 샘플을 저장할 ArrayList 생성
		ArrayList<ProductDTO> product = new ArrayList<>(); //상품 저장 배열

		// WebDriver 객체 생성
		//driver = new ChromeDriver();

		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성
		//JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성
		//JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// driver(웹 드라이버) 인스턴스를 JavascriptExecutor로 변환

		// 주소 설정(메인 페이지)
		String url = "https://www.moolban.com/";
		// 웹 페이지 접속
		driver.get(url);
		System.out.println("338 로그 url :["+url+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)
		// manage() : WebDriver.Options 인터페이스 반환 > 브라우저 관리 메서드 제공
		// window() : WebDriver.Options 인터페이스 메서드/ 브라우저 창 제어 하는 WebDriver.Window 객체 반환
		// maximize() : WebDriver.Window 객체 메서드/ 브라우저 창을 최대화(최대 크기)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// 현재 ChromeDriver의 대기 시간 20초를 부여
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// WebDriverWait : Selenium 클래스/ 조건이 만족될 때 까지 브라우저를 기다리게 함
		// driver를 사용 > 웹 페이지 제어
		// Duration : java 클래스/ 시간 설정()
		// ofSeconds : Duration 클래스의 정적 메서드/ 시간 표현
		//	>>seconds : 초 단위 정수값

		// 쿠키 팝업 닫기
		//"body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close"
		//WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close")));
		// WebElement : 요소를 객체로 반환
		// wait : WebDriverWait 객체/ 조건 만족 까지 기다림(20초)
		// wait.until() : 메서드 지정 조건 만족 까지 기다림/ css 선택자로 지정된 요소 페이지(가시성이 있을 때 까지 기다림)
		// ExpectedConditions : Selenium WebDriver에 다양한 조건 정의 메서드/ 웹 페이지 대기
		// visibilityOfElementLocated : 가시성(visible) 상태일 때까지 대기 조건/ 조건 충족 때까지 WebDriverWait대기
		// By.cssSelector : Selenium WebDriver에서 웹 페이지의 요소 찾기 위한 CSS선택자 사용하는 방법 제공

		// 쿠키 클릭
		//cookie.click();
		//click() : WebElement 클래스의 메서드/ 클릭 자동 수행
		// 상품 카테고리
		System.out.println("370 로그: 상품 카테고리 클릭 시작");
		// 바다 페이지 
		//"div.wrap_area > div.header_area > header > section > div > a:nth-child(2)"
		WebElement pruduct_sea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.wrap_area > div.header_area > header > section > div > a:nth-child(2)")));
		// 클릭
		pruduct_sea.click();
		System.out.println("376 로그: 낚시터 검색");

		try { 
			Thread.sleep(2000); // 메뉴 선택간 로딩 시간 2초
		} 
		catch (InterruptedException e) {
			System.err.println("382 로그: 로딩 실패");
			e.printStackTrace();
		}

		// 낚시터 클릭
		//"/html/body/div[1]/div[1]/div/div[2]/div[1]/section/ol/li[3]/a"
		WebElement pruduct_sea_fishing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div/div[2]/div[1]/section/ol/li[3]/a")));
		//WebElement pruduct_sea_fishing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.wrap_area > div.header_area > div > div.list_header > div.list_menu_area > section > ol > li.cc_key_2 > a")));
		System.out.println("390 로그: ["+pruduct_sea_fishing+"]");

		// 클릭
		//jsExecutor.executeScript("arguments[0].click()", pruduct_sea_fishing);
		pruduct_sea_fishing.click();
		System.out.println("395 로그: ["+pruduct_sea_fishing.getText()+"]");
		System.out.println("396 로그 : 낚시터 검색완료");

		// 상품 요소 저장 변수
		WebElement product_element = null;
		String href="";	// 상품 링크를 저장할 변수
		for(int i = 14; i < 20; i++) {           
			try {
				for(int a=14; a<=i;a++) {
					try {
						// 상품 요소 찾기
						//"#category_list > a:nth-child("+a+")"
						product_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#category_list > a:nth-child("+a+")"))); 
						href = product_element.getAttribute("href"); // 링크 href 추출
						// 상품 링크 확인
						System.out.println("410 상품 링크 :"+href);
					}
					catch (Exception e) {
						System.out.println("413 로그 : 요소 찾기 실패");
						continue;	// 요소를 찾지 못하면 계속 진행
					}
					//상품 요소 확인
					System.out.println("417 상품 요소 :["+product_element+"]");

					// 상품 요소가 화면에 보이도록 스크롤
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", product_element);
				}

				// 상품 페이지로 이동
				System.out.println("424 로그: 상품 페이지 이동");
				driver.get(href);

				// 객체 저장 
				ProductDTO productDTO = new ProductDTO();

				// 가격
				//"a > div > dl:nth-child(1) > dt > strong"
				try {
					List<WebElement> price_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a > div > dl:nth-child(1) > dt > strong")));
					System.out.println("434 로그 : "+price_Element + " : [" + price_Element.size()+"]");
					// 가격
					System.out.println("436 로그 : ["+price_Element.get(0).getText()+"]");
					productDTO.setProduct_price(Integer.parseInt(price_Element.get(0).getText().replace(",", "").replace("원전화예약","")));;
				}
				catch (Exception e) {
					System.out.println("440 로그 :가격 요소 찾기 실패");
					productDTO.setProduct_price(25000);	// 요소를 찾지 못하면 계속 진행
				}
				// 상품명
				//"div.profile_info > dl > dt > h1"
				try {
					List<WebElement> name_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.profile_info > dl > dt > h1"))); //body > div.wrap_area > div.view_area.container > section > div > div.profile_info > dl > dt > h1
					System.out.println("446 로그 : "+name_Element +" : [" + name_Element.size()+"]");
					// 상품명
					System.out.println("448 로그 :["+name_Element.get(0).getText()+"]");
					productDTO.setProduct_name(name_Element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("453 로그 :상품명 요소 찾기 실패");
					productDTO.setProduct_name("고래밥");	// 요소를 찾지 못하면 계속 진행
				}
				// 상품 주소
				//"#view_goods > section:nth-child(2) > div.view_box.view_map > div.map_link > h2 > a"
				try {	
					List<WebElement> address_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#view_goods > section:nth-child(2) > div.view_box.view_map > div.map_link > h2 > a")));
					//WebElement element =address_Element.get(0);
					System.out.println("461 로그 : "+address_Element + " : [" + address_Element.size()+"]");
					// 주소
					System.out.println("463 로그 : ["+address_Element.get(0).getText()+"]");
					productDTO.setProduct_address(address_Element.get(0).getText());		
				}
				catch (Exception e) {
					System.out.println("467 로그 :상품 주소 요소 찾기 실패");
					productDTO.setProduct_address("인천 부둣가");	// 요소를 찾지 못하면 계속 진행
				}

				// 이미지를 포함하는 요소를 찾기. (이미지 컨테이너의 CSS 선택자를 사용)
				By product_imgs = By.cssSelector("section > div > div.profile_slide > div.profile_list.company_image_list.swiper-wrapper");

				// 이미지 요소를 찾기 위한 CSS 선택자
				By image_Selector = By.cssSelector("img");

				// 이미지 컨테이너가 보일 때까지 기다림.
				WebElement image_Container = wait.until(ExpectedConditions.visibilityOfElementLocated(product_imgs));

				// 이미지 요소를 찾기.
				List<WebElement> product_images = image_Container.findElements(image_Selector);

				if (product_images.isEmpty()) { // 이미지 요소가 없을 시.
					System.out.println("484 로그: 이미지가 없음");
				} 
				else { // 이미지 요소가 있을 시
					System.out.println("487 로그: 이미지 개수 = [" + product_images.size()+"]");
					// 이미지 URL을 저장할 리스트 생성
					List<String> imageUrls = new ArrayList<>();
					// 각 이미지 요소의 src 속성 출력
					for (WebElement imgElement : product_images) {
						String img_src = imgElement.getAttribute("src");
						System.out.println("493 로그: 이미지 URL = [" + img_src+"]");
						imageUrls.add(img_src); // 리스트에 이미지 URL 추가
						productDTO.setUrl(imageUrls);
					}
				}

				// 결과 추출
				//System.out.println("500 결과"+product);
				product.add(productDTO); // 배열리스트에 객체 추가
				driver.navigate().back(); // 브라우저의 이전 페이지로 이동 

			}catch (Exception e) {
				// 오류가 발생시 다음으로 넘어감
				System.err.println("538 로그 오류 발생: [" +i+"]");
				e.printStackTrace();
			}
		}
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductSeaFishing 종료");

		//드라이버 종료
		//driver.quit();
		return product;			

	}
	//-----------------------------------------------------------------------	
	public static ArrayList<ProductDTO> makeSampleProductFreshWaterFishing() { // 민물 낚시터
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductFreshWater 시작");

		//상품 샘플을 저장할 ArrayList 생성
		ArrayList<ProductDTO> product = new ArrayList<>(); //상품 저장 배열

		//WebDriver 객체 생성
		//driver = new ChromeDriver();

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성
		//JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// driver(웹 드라이버) 인스턴스를 JavascriptExecutor로 변환

		// 주소 설정(메인 페이지)
		String url = "https://www.moolban.com/";
		// 웹 페이지 접속
		driver.get(url);
		System.out.println("536 로그 url :["+url+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)
		// manage() : WebDriver.Options 인터페이스 반환 > 브라우저 관리 메서드 제공
		// window() : WebDriver.Options 인터페이스 메서드/ 브라우저 창 제어 하는 WebDriver.Window 객체 반환
		// maximize() : WebDriver.Window 객체 메서드/ 브라우저 창을 최대화(최대 크기)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// 현재 ChromeDriver의 대기 시간 20초를 부여
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// WebDriverWait : Selenium 클래스/ 조건이 만족될 때 까지 브라우저를 기다리게 함
		// driver를 사용 > 웹 페이지 제어
		// Duration : java 클래스/ 시간 설정()
		// ofSeconds : Duration 클래스의 정적 메서드/ 시간 표현
		//	>>seconds : 초 단위 정수값

		// 쿠키 팝업 닫기
		//"body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close"
		//WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close")));
		// WebElement : 요소를 객체로 반환
		// wait : WebDriverWait 객체/ 조건 만족 까지 기다림(20초)
		// wait.until() : 메서드 지정 조건 만족 까지 기다림/ css 선택자로 지정된 요소 페이지(가시성이 있을 때 까지 기다림)
		// ExpectedConditions : Selenium WebDriver에 다양한 조건 정의 메서드/ 웹 페이지 대기
		// visibilityOfElementLocated : 가시성(visible) 상태일 때까지 대기 조건/ 조건 충족 때까지 WebDriverWait대기
		// By.cssSelector : Selenium WebDriver에서 웹 페이지의 요소 찾기 위한 CSS선택자 사용하는 방법 제공

		// 쿠키 클릭
		//cookie.click();
		//click() : WebElement 클래스의 메서드/ 클릭 자동 수행

		// 상품 카테고리
		System.out.println("569 로그: 상품 카테고리 클릭 시작");
		// 민물 페이지
		//"div.wrap_area > div.header_area > header > section > div > a:nth-child(3)"
		WebElement pruduct_fresh_water = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.wrap_area > div.header_area > header > section > div > a:nth-child(3)")));
		// 클릭
		pruduct_fresh_water.click();

		System.out.println("576 로그: 수상 검색");

		try { 
			Thread.sleep(2000); // 메뉴 선택간 로딩 시간 2초
		} catch (InterruptedException e) {
			System.err.println("581 로그 : 로딩 실패");
			e.printStackTrace();
		}

		// 수상 클릭
		//"/html/body/div[1]/div[1]/div/div[2]/div[1]/section/ol/li[3]/a"
		WebElement pruduct_freshwater_fishing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div/div[2]/div[1]/section/ol/li[3]/a")));
		//WebElement pruduct_sea_fishing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.wrap_area > div.header_area > div > div.list_header > div.list_menu_area > section > ol > li.cc_key_2 > a")));
		System.out.println("589 로그 :["+pruduct_freshwater_fishing+"]");
		// 클릭
		//jsExecutor.executeScript("arguments[0].click()", pruduct_sea_fishing);
		pruduct_freshwater_fishing.click();	
		System.out.println("593 로그: ["+pruduct_freshwater_fishing.getText()+"]");
		System.out.println("594 로그 : 낚시터 검색완료");
		// 상품 요소 저장 변수
		WebElement pruduct_element = null;
		String href="";	// 상품 링크를 저장할 변수
		for(int i = 4; i < 10; i++) {           
			try {
				for(int a=4; a<=i;a++) {
					try {
						// 상품 요소 찾기
						// pruduct_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#category_list > a:nth-child("+a+").list_box_area.list_ad_box_area.list_ad_box_area4")));                  
						//"#category_list > a:nth-child("+a+")"
						pruduct_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#category_list > a:nth-child("+a+")")));
						href = pruduct_element.getAttribute("href"); // 링크 href 추출

						// 상품 링크 확인
						System.out.println("609 로그 상품 링크 : ["+href+"]");

					}catch (Exception e) {
						System.err.println("612 로그 상품 요소를 찾지 못함.");
						continue;	// 요소를 찾지 못하면 계속 진행
					}
					//상품 요소 확인
					System.out.println("616 로그 상품 요소 : ["+pruduct_element+"]");
					// 상품 요소가 화면에 보이도록 스크롤
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", pruduct_element);
					// jsExecutor : JavascriptExecutor 인터페이스의 인스턴스로, JavaScript 코드를 실행할 수 있는 WebDriver 객체
					//  > DOM에 직접 접근하여 JavaScript를 실행
					// executeScript() :  JavaScript 코드를 실행
					// arguments[0] : arguments 배열의 첫 번째 요소를 참조(product_element 배열의 첫 번째 요소로 전달)
					// scrollIntoView(true) : 요소가 화면에 표시되도록 스크롤
					// > true: 요소가 화면의 상단에 맞추어 스크롤/ false: 요소가 화면의 하단에 맞추어 스크롤
				}

				// 상품 페이지로 이동
				System.out.println("628 로그: 상품 페이지 이동");
				driver.get(href);
				//웹 브라우저를 특정 URL(href)로 이동

				// 객체 저장 
				ProductDTO productDTO = new ProductDTO();

				// 남은 인원 요소 
				//a:nth-child(1) > div > span
				try {
					List<WebElement> people_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a:nth-child(1) > div > span")));
					System.out.println("639 로그:"+people_Element +" : [" + people_Element.size()+"]");
					//Selenium WebDriver를 사용 
					//웹 페이지에서 특정 CSS 선택자와 일치하는 모든 요소를 찾고, 
					//이 요소들이 가시성 상태가 될 때까지 기다리는 기능을 수행

					// 남은 인원 객체에 저장
					System.out.println("645 로그:["+people_Element.get(0).getText()+"]");
					productDTO.setProduct_cnt(Integer.parseInt(people_Element.get(0).getText().replace("남은수", "").replace("동", "").replace("명","")));;
					// Integer.parseInt : 문자열을 정수로 변환 메서드
					// get(0) : 리스트 첫 번째 요소
					// getText() : 요소의 택스트를 가져옴
					// replace() : 문자열 특정 부분을 다른 문자열로 교체 메서드
				}
				catch (Exception e) {
					System.out.println("653 로그 :남은 인원 요소 찾기 실패");
					productDTO.setProduct_cnt(5);	// 요소를 찾지 못하면 계속 진행
				}
				// 가격
				//a:nth-child(1) > div > div.reserve_price > p
				try {
					List<WebElement> price_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a:nth-child(1) > div > div.reserve_price > p")));
					System.out.println("660 로그:"+price_Element + " : [" + price_Element.size()+"]");
					// 가격 객체에 저장
					System.out.println("662 로그:["+price_Element.get(0).getText()+"]");
					productDTO.setProduct_price(Integer.parseInt(price_Element.get(0).getText().replace(",", "").replace(" 원", "").replace("현장결제", "").replace("원","")));;

				}
				catch (Exception e) {
					System.out.println("667 로그 :가격 요소 찾기 실패");
					productDTO.setProduct_price(50000);	// 요소를 찾지 못하면 계속 진행
				}
				// 상품명
				//div.profile_info > dl > dt > h1
				try {
					List<WebElement> name_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.profile_info > dl > dt > h1")));
					System.out.println("674 로그:"+name_Element +" : [" + name_Element.size()+"]");
					// 상품명 객체에 저장
					System.out.println("676 로그:["+name_Element.get(0).getText()+"]");
					productDTO.setProduct_name(name_Element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("680 로그 :상품명 요소 찾기 실패");
					productDTO.setProduct_name("행복 낚시");	// 요소를 찾지 못하면 계속 진행
				}
				// 상품 주소
				//body > div.wrap_area > div.view_area.container > section > div > div.profile_info > dl > dd:nth-child(2) > h2
				try {
					List<WebElement> address_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(" section > div > div.profile_info > dl > dd:nth-child(2) > h2")));
					System.out.println("687 로그: "+address_Element + " : [" + address_Element.size()+"]");
					// 상품 주소 객체에 저장
					System.out.println("689 로그:["+address_Element.get(0).getText()+"]");
					productDTO.setProduct_address(address_Element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("693 로그 :상품 주소 요소 찾기 실패");
					productDTO.setProduct_address("연안 부두");	// 요소를 찾지 못하면 계속 진행
				}

				//상품 이미지
				// 이미지를 포함하는 요소를 찾기. (이미지 컨테이너의 CSS 선택자를 사용)
				By product_imgs = By.cssSelector("section > div > div.profile_slide > div.profile_list.company_image_list.swiper-wrapper");

				// 이미지 요소를 찾기 위한 CSS 선택자
				By image_Selector = By.cssSelector("img");

				// 이미지 컨테이너가 보일 때까지 기다림.
				WebElement image_Container = wait.until(ExpectedConditions.visibilityOfElementLocated(product_imgs));

				// 이미지 요소를 찾기.
				List<WebElement> product_images = image_Container.findElements(image_Selector);

				if (product_images.isEmpty()) { // 이미지 요소가 없을 시.
					System.out.println("711 로그: 이미지가 없음");
				} 
				else { // 이미지 요소가 있을 시
					System.out.println("714 로그: 이미지 개수 = [" + product_images.size()+"]");
					// 이미지 URL을 저장할 리스트 생성
					List<String> imageUrls = new ArrayList<>();
					// 각 이미지 요소의 src 속성 출력
					for (WebElement imgElement : product_images) {
						String img_src = imgElement.getAttribute("src");
						System.out.println("237 로그: 이미지 URL = [" + img_src+"]");
						imageUrls.add(img_src); // 리스트에 이미지 URL 추가
						productDTO.setUrl(imageUrls);
					}
				}
				// 결과 추출
				//System.out.println("109 결과"+product);
				product.add(productDTO); //배열리스트에 객체 추가
				driver.navigate().back();
				// navigate() : WebDriver의 메서드/ 페이지 이동, 뒤로 가기, 앞으로 가기, 새로 고침과 같은 탐색 작업을 수행
				// back() : Navigation 인터페이스의 메서드/ 뒤로 가기
				// 브라우저의 이전 페이지로 이동

			}catch (Exception e) {
				// 오류가 발생시 다음으로 넘어감
				System.err.println("763 로그 오류 발생: [" +i+"]");
				e.printStackTrace();
			}
		}
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductFreshWater 종료");
		// 드라이버 종료
		//driver.quit();
		// quit() : WebDriver 클래스의 메서드/ 현재의 WebDriver 세션을 종료
		return product;		
	}
	//-----------------------------------------------------------------------	
	public static ArrayList<ProductDTO> makeSampleProductFreshWaterFishingFishingCafe() { // 낚시 카페
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductFreshWaterFishingFishingCafe 시작");

		//상품 샘플을 저장할 ArrayList 생성
		ArrayList<ProductDTO> product = new ArrayList<>(); //상품 저장 배열

		//WebDriver 객체 생성
		//driver = new ChromeDriver();

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성
		//JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// driver(웹 드라이버) 인스턴스를 JavascriptExecutor로 변환

		// 주소 설정(메인 페이지)
		String url = "https://www.moolban.com/";
		// 웹 페이지 접속
		driver.get(url);
		System.out.println("764 로그 url :["+url+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)
		// manage() : WebDriver.Options 인터페이스 반환 > 브라우저 관리 메서드 제공
		// window() : WebDriver.Options 인터페이스 메서드/ 브라우저 창 제어 하는 WebDriver.Window 객체 반환
		// maximize() : WebDriver.Window 객체 메서드/ 브라우저 창을 최대화(최대 크기)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// 현재 ChromeDriver의 대기 시간 20초를 부여
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// WebDriverWait : Selenium 클래스/ 조건이 만족될 때 까지 브라우저를 기다리게 함
		// driver를 사용 > 웹 페이지 제어
		// Duration : java 클래스/ 시간 설정()
		// ofSeconds : Duration 클래스의 정적 메서드/ 시간 표현
		//	>>seconds : 초 단위 정수값

		// 쿠키 팝업 닫기
		//"body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close"
		//WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close")));
		// WebElement : 요소를 객체로 반환
		// wait : WebDriverWait 객체/ 조건 만족 까지 기다림(20초)
		// wait.until() : 메서드 지정 조건 만족 까지 기다림/ css 선택자로 지정된 요소 페이지(가시성이 있을 때 까지 기다림)
		// ExpectedConditions : Selenium WebDriver에 다양한 조건 정의 메서드/ 웹 페이지 대기
		// visibilityOfElementLocated : 가시성(visible) 상태일 때까지 대기 조건/ 조건 충족 때까지 WebDriverWait대기
		// By.cssSelector : Selenium WebDriver에서 웹 페이지의 요소 찾기 위한 CSS선택자 사용하는 방법 제공

		// 쿠키 클릭
		//cookie.click();
		//click() : WebElement 클래스의 메서드/ 클릭 자동 수행

		// 상품 카테고리
		System.out.println("797 로그: 상품 카테고리 클릭 시작");
		// 민물 페이지
		//"div.wrap_area > div.header_area > header > section > div > a:nth-child(3)"
		WebElement pruduct_fresh_water = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.wrap_area > div.header_area > header > section > div > a:nth-child(3)")));
		// 클릭
		pruduct_fresh_water.click();

		System.out.println("804 로그: 수상 검색");

		try { 
			Thread.sleep(2000); // 메뉴 선택간 로딩 시간 2초
		} catch (InterruptedException e) {
			System.err.println("809 로그 : 로딩 실패");
			e.printStackTrace();
		}

		// 낚시 카페 클릭
		//"/html/body/div[1]/div[1]/div/div[2]/div[1]/section/ol/li[4]/a"
		WebElement product_freshwater_fishing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div/div[2]/div[1]/section/ol/li[4]/a")));
		//WebElement pruduct_sea_fishing = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.wrap_area > div.header_area > div > div.list_header > div.list_menu_area > section > ol > li.cc_key_2 > a")));
		System.out.println("817 로그 :["+product_freshwater_fishing+"]");

		// 클릭
		//jsExecutor.executeScript("arguments[0].click()", pruduct_sea_fishing);
		product_freshwater_fishing.click();
		System.out.println("822 로그: ["+product_freshwater_fishing.getText()+"]");
		System.out.println("823 로그 : 낚시터 검색완료");
		// 상품 요소 저장 변수
		WebElement product_element = null;
		String href="";	// 상품 링크를 저장할 변수
		for (int i = 4; i < 10; i++) {   
			try {				
				for(int a=4; a<=i;a++) {
					try {
						// 상품 요소 찾기
						//#category_list > a:nth-child(4)
						product_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#category_list > a:nth-child(" + a + ")")));
						href = product_element.getAttribute("href"); // 링크 href 추출
						// 상품 링크 확인
						System.out.println("836 상품 링크 :["+href+"]");
					}
					catch (Exception e) {
						System.out.println("839 로그 : 요소 찾기 실패");
						continue;	// 요소를 찾지 못하면 계속 진행
					}
					//상품 요소 확인
					System.out.println("843 로그 상품 요소 :["+product_element+"]");

					// 상품 요소가 화면에 보이도록 스크롤
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", product_element);
					// jsExecutor : JavascriptExecutor 인터페이스의 인스턴스로, JavaScript 코드를 실행할 수 있는 WebDriver 객체
					//  > DOM에 직접 접근하여 JavaScript를 실행
					// executeScript() :  JavaScript 코드를 실행
					// arguments[0] : arguments 배열의 첫 번째 요소를 참조(product_element 배열의 첫 번째 요소로 전달)
					// scrollIntoView(true) : 요소가 화면에 표시되도록 스크롤
					// > true: 요소가 화면의 상단에 맞추어 스크롤/ false: 요소가 화면의 하단에 맞추어 스크롤
				}

				// 상품 페이지로 이동
				System.out.println("856 로그 : 상품 페이지 이동");
				driver.get(href);
				//웹 브라우저를 특정 URL(href)로 이동

				System.out.println("860 로그 : 이용정보 페이지 이동");
				// 이용정보 페이지
				//body > div.wrap_area > div.view_area.container > div.view_tab_area > section > ul > li:nth-child(3) > a
				WebElement pruduct_fresh_utilization = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wrap_area > div.view_area.container > div.view_tab_area > section > ul > li:nth-child(3) > a")));
				// 클릭
				pruduct_fresh_utilization.click();

				System.out.println("867 로그: 이용정보 페이지 이동 완료");

				try { 
					Thread.sleep(2000); // 메뉴 선택간 로딩 시간 2초
				} catch (InterruptedException e) {
					System.err.println("872 로그 : 로딩 실패");
					e.printStackTrace();
				}

				// 객체 저장 
				ProductDTO productDTO = new ProductDTO();

				// 가격
				//#view_goods > section:nth-child(1) > div.view_box.info_view_sty5 > table > tbody > tr:nth-child(2) > td:nth-child(1) > strong
				try {
					List<WebElement> price_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.view_box.info_view_sty5 > table > tbody > tr:nth-child(2) > td:nth-child(1) > strong")));
					System.out.println("883 로그:"+price_Element + " : [" + price_Element.size()+"]");
					// 가격 객체에 저장
					System.out.println("885 로그:["+price_Element.get(0).getText()+"]");
					productDTO.setProduct_price(Integer.parseInt(price_Element.get(0).getText().replace(",", "").replace("원", "")));;

				}
				catch (Exception e) {
					System.out.println("890 로그 :가격 요소 찾기 실패");
					productDTO.setProduct_price(50000);	// 요소를 찾지 못하면 계속 진행
				}
				// 상품명
				//body > div.wrap_area > div.view_area.container > section > div > div.profile_info > dl > dt > h1
				try {
					List<WebElement> name_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("section > div > div.profile_info > dl > dt > h1")));
					System.out.println("897 로그:"+name_Element +" : [" + name_Element.size()+"]");
					// 상품명 객체에 저장
					System.out.println("899 로그:["+name_Element.get(0).getText()+"]");
					productDTO.setProduct_name(name_Element.get(0).getText());

				}
				catch (Exception e) {
					System.out.println("904 로그 :상품명 찾기 실패");
					productDTO.setProduct_name("고래밥");	// 요소를 찾지 못하면 계속 진행
				}
				// 상품 주소
				//#view_goods > section:nth-child(1) > div.view_box.view_map > div.map_link > h2 > a
				try {
					List<WebElement> address_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(" div.view_box.view_map > div.map_link > h2 > a")));
					System.out.println("911 로그: "+address_Element + " : [" + address_Element.size()+"]");

					// 상품 주소 객체에 저장
					System.out.println("914 로그:["+address_Element.get(0).getText()+"]");
					productDTO.setProduct_address(address_Element.get(0).getText());
				}
				catch (Exception e) {
					System.out.println("918 로그 :상품명 찾기 실패");
					productDTO.setProduct_address("부산 앞바다");	// 요소를 찾지 못하면 계속 진행
				}

				//상품 이미지
				// 이미지를 포함하는 요소를 찾기. (이미지 컨테이너의 CSS 선택자를 사용)
				By product_imgs = By.cssSelector("section > div > div.profile_slide > div.profile_list.company_image_list.swiper-wrapper");

				// 이미지 요소를 찾기 위한 CSS 선택자
				By image_Selector = By.cssSelector("img");

				// 이미지 컨테이너가 보일 때까지 기다림.
				WebElement image_Container = wait.until(ExpectedConditions.visibilityOfElementLocated(product_imgs));

				// 이미지 요소를 찾기.
				List<WebElement> product_images = image_Container.findElements(image_Selector);

				if (product_images.isEmpty()) { // 이미지 요소가 없을 시.
					System.out.println("936 로그: 이미지가 없음");
				} 
				else { // 이미지 요소가 있을 시
					System.out.println("939 로그: 이미지 개수 = [" + product_images.size()+"]");
					// 이미지 URL을 저장할 리스트 생성
					List<String> imageUrls = new ArrayList<>();
					// 각 이미지 요소의 src 속성 출력
					for (WebElement imgElement : product_images) {
						String img_src = imgElement.getAttribute("src");
						System.out.println("237 로그: 이미지 URL = [" + img_src+"]");
						imageUrls.add(img_src); // 리스트에 이미지 URL 추가
						productDTO.setUrl(imageUrls);
					}
				}
				// 결과 추출
				//System.out.println("952 결과"+product);
				product.add(productDTO); //배열리스트에 객체 추가
				driver.navigate().back();
				// navigate() : WebDriver의 메서드/ 페이지 이동, 뒤로 가기, 앞으로 가기, 새로 고침과 같은 탐색 작업을 수행
				// back() : Navigation 인터페이스의 메서드/ 뒤로 가기
				// 브라우저의 이전 페이지로 이동
			}
			catch (Exception e) {
				// 오류가 발생시 다음으로 넘어감
				System.err.println("960 로그 오류 발생: [" +i+"]");
				e.printStackTrace();
			}
		}
		System.out.println(" model.common.CrawlingSelenium.makeSampleProductFreshWaterFishingFishingCafe 종료");

		//드라이버 종료
		//driver.quit();
		// quit() : WebDriver 클래스의 메서드/ 현재의 WebDriver 세션을 종료
		return product;			

	}
	//-----------------------------------------------------------------------	
	public static ArrayList<BoardDTO> makeSampleBoard() { // 게시판
		System.out.println(" model.common.CrawlingSelenium.makeSampleBoard 시작");

		//게시판 샘플을 저장할 ArrayList 생성
		ArrayList<BoardDTO> board = new ArrayList<>(); //상품 저장 배열

		//WebDriver 객체 생성
		//driver = new ChromeDriver();

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver; // JavaScript 실행
		// JavaScript를 실행할 수 있는 `JavascriptExecutor` 객체 생성
		//JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// driver(웹 드라이버) 인스턴스를 JavascriptExecutor로 변환

		// 주소 설정(메인 페이지)
		String url = "https://www.moolban.com/";
		// 웹 페이지 접속
		driver.get(url);
		System.out.println("991 로그 url :["+url+"]");

		// 브라우저 전체화면으로 변경
		driver.manage().window().maximize();
		// 현재 사용중인 driver(웹 드라이버)
		// manage() : WebDriver.Options 인터페이스 반환 > 브라우저 관리 메서드 제공
		// window() : WebDriver.Options 인터페이스 메서드/ 브라우저 창 제어 하는 WebDriver.Window 객체 반환
		// maximize() : WebDriver.Window 객체 메서드/ 브라우저 창을 최대화(최대 크기)

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// 현재 ChromeDriver의 대기 시간 20초를 부여
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// WebDriverWait : Selenium 클래스/ 조건이 만족될 때 까지 브라우저를 기다리게 함
		// driver를 사용 > 웹 페이지 제어
		// Duration : java 클래스/ 시간 설정()
		// ofSeconds : Duration 클래스의 정적 메서드/ 시간 표현
		//	>>seconds : 초 단위 정수값

		// 쿠키 팝업 닫기
		//"body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close"
		//WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wrap_area > div.wrap_area.contest_popup > div > div > div.fornt_btn > a.popup_close")));
		// WebElement : 요소를 객체로 반환
		// wait : WebDriverWait 객체/ 조건 만족 까지 기다림(20초)
		// wait.until() : 메서드 지정 조건 만족 까지 기다림/ css 선택자로 지정된 요소 페이지(가시성이 있을 때 까지 기다림)
		// ExpectedConditions : Selenium WebDriver에 다양한 조건 정의 메서드/ 웹 페이지 대기
		// visibilityOfElementLocated : 가시성(visible) 상태일 때까지 대기 조건/ 조건 충족 때까지 WebDriverWait대기
		// By.cssSelector : Selenium WebDriver에서 웹 페이지의 요소 찾기 위한 CSS선택자 사용하는 방법 제공

		// 쿠키 클릭
		//cookie.click();
		//click() : WebElement 클래스의 메서드/ 클릭 자동 수행

		// 게시판
		System.out.println("1026 로그: 게시판 클릭 시작");
		try {
			// 게시판 페이지
			//body > div.wrap_area > div.header_area > header > section > div > a:nth-child(6)
			WebElement notice_board = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.header_area > header > section > div > a:nth-child(6)")));
			// 클릭
			System.out.println("1032 로그 게시판 요소:["+notice_board+"]");
			notice_board.click();

			System.out.println("1035 로그: 자유 게시판 검색");

			try { 
				Thread.sleep(4000); // 메뉴 선택간 로딩 시간 4초
			} catch (InterruptedException e) {
				System.err.println("1040 로그 : 로딩 실패");
				e.printStackTrace();
			}

			// 자유게시판 클릭
			///html/body/div[1]/div[39]/div/div[2]/div[1]/section/ul/li[3]/a
			WebElement free_board = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[1]/div[39]/div/div[2]/div[1]/section/ul/li[3]/a")));
			System.out.println("1047 로그 자유게시판 요소:["+free_board+"]");
			free_board.click();// 클릭
			System.out.println("1049 로그 자유게시판 텍스트:["+free_board.getText()+"]");
			System.out.println("1050 로그 : 자유 게시판 검색 완료");

			try { 
				Thread.sleep(4000); // 메뉴 선택간 로딩 시간 4초
			} catch (InterruptedException e) {
				System.err.println("1055 로그 : 로딩 실패");
				e.printStackTrace();
			}

			// 게시판들 요소 찾기 변수
			List<WebElement> board_elements = null;
			String href="";	
			// 게시판 요소
			String boardElement = "div.talk_list > #talk_detail_list > div.talk_box_area > a.talk_view_btn";

			for(int i = 1; i <= 10; i++) {   
				try {				
					// 게시판 요소 찾기 FIXME 요소 고쳐야함
					board_elements = driver.findElements(By.cssSelector(boardElement));
					if (i >= board_elements.size()) {
						System.err.println("1070 로그 요소가 부족 인덱스 [" + i + "]에 접근 불가");
						break;
					}
					WebElement board_element = board_elements.get(i);
					href = board_element.getAttribute("href"); // 링크 href 추출

					// 게시판 링크 확인
					System.out.println("1077 로그 게시판 링크 :["+href+"]");

					//게시판 요소 확인
					System.out.println("1080 로그 게시판 요소 :"+board_elements);
					// 게시판 요소가 화면에 보이도록 스크롤
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", board_element);
					// jsExecutor : JavascriptExecutor 인터페이스의 인스턴스로, JavaScript 코드를 실행할 수 있는 WebDriver 객체
					//  > DOM에 직접 접근하여 JavaScript를 실행
					// executeScript() :  JavaScript 코드를 실행
					// arguments[0] : arguments 배열의 첫 번째 요소를 참조(product_element 배열의 첫 번째 요소로 전달)
					// scrollIntoView(true) : 요소가 화면에 표시되도록 스크롤
					// > true: 요소가 화면의 상단에 맞추어 스크롤/ false: 요소가 화면의 하단에 맞추어 스크롤

					// 게시판 페이지로 이동
					System.out.println("1091 로그: 게시판 페이지 이동");
					driver.get(href);
					//웹 브라우저를 특정 URL(href)로 이동

					// 객체에 저장
					BoardDTO boardDTO=new BoardDTO();

					//제목
					//body > div.wrap_area > div.view_detail.container > div > div.detail_title > section > div > dl > dt
					try {
						List<WebElement> board_title_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div > div.detail_title > section > div > dl > dt")));
						System.out.println("1102 로그 제목 요소 개수:"+board_title_Element +" : [" + board_title_Element.size()+"]");
						//Selenium WebDriver를 사용 
						//웹 페이지에서 특정 CSS 선택자와 일치하는 모든 요소를 찾고, 
						//이 요소들이 가시성 상태가 될 때까지 기다리는 기능을 수행
						// 제목 객체에 저장
						System.out.println("1107 로그 제목 :["+board_title_Element.get(0).getText()+"]");
						if(!board_title_Element.isEmpty()) {
							boardDTO.setBoard_title(board_title_Element.get(0).getText());
							// Integer.parseInt : 문자열을 정수로 변환 메서드
							// get(0) : 리스트 첫 번째 요소
							// getText() : 요소의 택스트를 가져옴
							// replace() : 문자열 특정 부분을 다른 문자열로 교체 메서드
						}				
					}
					catch (Exception e) {
						System.out.println("1117 로그 :제목 찾기 실패");
						boardDTO.setBoard_title("제목이 없습니다.");
					}
					// 작성자
					//body > div.wrap_area > div.view_detail.container > div > div.detail_profile.clearfix > section > p
//					List<WebElement> board_writer_Element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div > div.detail_profile.clearfix > section > p")));
//					System.out.println("1157 로그 작성자 요소 개수:"+board_writer_Element +" : [" + board_writer_Element.size()+"]");
//					// 작성자 객체에 저장
//					System.out.println("1320 로그 작성자:["+board_writer_Element.get(0).getText()+"]");
//					if(!board_writer_Element.isEmpty()) {
//						boardDTO.setBoard_writer_id(board_writer_Element.get(0).getText());
//					}
					//FIXME
					try {
						// 작성자 넣을 맴버 객체 생성
						MemberDTO memberDTO=new MemberDTO();
						MemberDAO memberDAO=new MemberDAO();
						
						
						Random random = new Random();
						
						// 맴버의 pk인 작성자를 받아오기위해 select 한다.
						memberDTO.setMember_condition("RANDOM_MEMBER_ID");
						memberDTO = memberDAO.selectOne(memberDTO);
						//ArrayList<MemberDTO> members=memberDAO.selectAll(memberDTO);
						//int randomIndex = random.nextInt(members.size()); // 0부터 members.size() - 1까지의 랜덤 인덱스
						//MemberDTO randomMember = members.get(randomIndex);
						//랜덤으로 설정한 member id를 게시판 작성자에 넣어준다.
						boardDTO.setBoard_writer_id(memberDTO.getMember_id());
						
					}
					catch (Exception e) {
						System.out.println("1143 로그 :작성자 찾기 실패");
						boardDTO.setBoard_writer_id("MOOMOO@naver.com");
					}
					// 내용 FIXME 내용만 받는 요소 > 
					//body > div.wrap_area > div.view_detail.container > section > div > div.manage_detail
					// 이거 안에서 P 태그면 댓글 P태그가 아니면 IMG 가져오기
					//body > div.wrap_area > div.view_detail.container > section > div > div.manage_detail > p
					//					List<WebElement> board_contents_Element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("section > div > div.manage_detail")));
					//					System.out.println("959 로그 내용 요소 개수: " + board_contents_Element.size());
					//List<WebElement> board_imag_Element = new ArrayList<>();
					//List<WebElement> board_content_Element = new ArrayList<>();
					// 내용을 가져오는데
					//ImageFileDTO image = null;
					// 내용이 없는 경우
					//if(!board_contents_Element.isEmpty()) {
					// FIXME	
					//By selector_img =By.cssSelector("img");
					//By selector_text=By.cssSelector("p");
					////이미지 요소 비교(들어 있는지 없는지)
					//List<WebElement> images = driver.findElements(selector_img);
					//List<WebElement> texts = driver.findElements(selector_text);

					//if(!images.isEmpty() && !texts.isEmpty()) {
					// 요소가 전부 있다면
					//body > div.wrap_area > div.view_detail.container > section > div > div.manage_detail > div:nth-child(1) > img
					
					//내용 요소
					//section > div > div.manage_detail > p
					try {
						List<WebElement> board_content_Element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("section > div > div.manage_detail > p")));
						// 요소가 비어 있는 경우, 즉 `board_content_Element` 리스트가 빈 경우 `NoSuchElementException`을 발생.
						// 이는 요소가 예상대로 로드되지 않았음을 명시적으로 표시하기 위함.

						if (board_content_Element.isEmpty()) { // 요소가 없다면
							throw new NoSuchElementException("1177 로그: 요소를 찾지 못함");
							// throw: 특정 조건 예외 발생
							// NoSuchElementException : 요소를 찾지 못할때 발생하는 예외 클래스	
						}
						// 로드된 내용 요소들의 개수를 출력.
						System.out.println("1182 로그 내용 요소 개수: [" + board_content_Element.size()+"]");

						// 첫 번째 내용 요소의 텍스트를 출력.
						System.out.println("1185 로그 내용 : [" + board_content_Element.get(0).getText()+"]");

						// 첫 번째 내용 요소의 텍스트를 `data` 객체의 `board_content` 속성에 저장.
						boardDTO.setBoard_content(board_content_Element.get(0).getText());
					} 
//					catch (TimeoutException e) {
//						System.out.println("1190 로그 에러 :[" + e.getMessage()+"]");
//						boardDTO.setBoard_content("없는 내용입니다.");
//					} 
					catch (NoSuchElementException e) {
						System.out.println("1194 로그 에러: [" + e.getMessage()+"]");
						boardDTO.setBoard_content("없는 내용입니다.");
					} catch (Exception e) {
						System.out.println("1196 로그 에러: [" + e.getMessage()+"]");
						boardDTO.setBoard_content("없는 내용입니다.");
					}
					// 이미지 저장
					//section > div > div.manage_detail img
					try {
						List<WebElement> board_imag_Element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("section > div > div.manage_detail img")));
						System.out.println("1203 로그 이미지 요소 개수: [" + board_imag_Element.size()+"]");
						
						List<String> imageUrls = new ArrayList<>();
						// 이미지 저장
						for (WebElement img : board_imag_Element) {
							//image=new ImageFileDTO();
							// 이미지 요소 가져오기
							String imageUrl = img.getAttribute("src");
							System.out.println("1225 로그 이미지 src: ["+ imageUrl+"]");
							// 이미지src DTO 저장
							imageUrls.add(imageUrl);
							boardDTO.setUrl(imageUrls);                
							//image.setFile_dir(imageUrl);
						}	
					}
					catch (Exception e) { 
						// 이미지가 없는경우
						//System.err.println("980로그 Timed out waiting for images to be present: " + e.getMessage());
						System.out.println("1221 로그:이미지 없음");
					}
					
					// 추가적인 디버깅 작업
					//								if(images.isEmpty()) { // 이미지가 없다면
					//									// 내용만 받아와
					//									board_content_Element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("section > div > div.manage_detail > p")));
					//									System.out.println("984 로그 댓글 요소 개수: " + board_content_Element.size());
					// 내용 저장


					//								}
					//								else if(texts.isEmpty()) {// 내용이 없다면
					//									// 이미지만 받아와
					//									
					//								}

					//}
					// 이미지 객체에 저장
					//						System.out.println("1004 로그 작성자:"+board_imag_Element.get(0).getText());
					//						if(!board_imag_Element.isEmpty()) {
					//							data.setBoard_writer_id(board_imag_Element.get(0).getText());
					//						}
					//						// 내용 객체에 저장
					//						System.out.println("1009 로그 작성자:"+board_content_Element.get(0).getText());
					//						if(!board_content_Element.isEmpty()) {
					//							data.setBoard_writer_id(board_content_Element.get(0).getText());
					//						}

					//					}
					//둘다 있으면 둘다 받아줘

					//						if(!board_imag_Element.isEmpty()&&!board_content_Element.isEmpty()) {
					//							for (WebElement img : board_imag_Element) {
					//								//image=new ImageFileDTO();
					//								// 이미지 요소 가져오기
					//								String imageUrl = img.getAttribute("src");
					//								// 이미지src DTO 저장
					//								data.setBoard_file_dir(imageUrl);		                
					//								//image.setFile_dir(imageUrl);
					//							}
					//							data.setBoard_content(board_content_Element.get(0).getText());
					//
					//						}	
					//						else if(board_content_Element.isEmpty()) {
					//							for (WebElement img : board_imag_Element) {
					//								//image=new ImageFileDTO();
					//								// 이미지 요소 가져오기
					//								String imageUrl = img.getAttribute("src");
					//								// 이미지src DTO 저장
					//								data.setBoard_file_dir(imageUrl);		                
					//								//image.setFile_dir(imageUrl);
					//							}
					//						}
					//						else if(board_imag_Element.isEmpty()){
					//							data.setBoard_content(board_content_Element.get(0).getText());
					//						}
					//
					//					}
					//					if(board_contents_Element!=null){
					//						List<WebElement> board_content_Element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("section > div > div.manage_detail > p")));
					//
					//						// 내용이 있고, 이미지가 있으면
					//						for(WebElement board_content : board_content_Element) {
					//							// 가져온 요소만큼 반복 시켜서
					//							//내용 저장
					//							data.setBoard_content(board_content_Element.get(0).getText());
					//
					//						}
					//					}

					//imageFileDTO.add(image);


					//					// 내용 객체에 저장
					//					System.out.println("970 로그 내용:"+board_content_Element.get(0).getText());
					//					if(!board_content_Element.isEmpty()) {
					//						data.setBoard_content(board_content_Element.get(0).getText());
					//					}

					// 결과 추출
					//System.out.println("1302 결과"+data);
					board.add(boardDTO); //배열리스트에 객체 추가

					// 뒤로 가기
					driver.navigate().back();
					// navigate() : WebDriver의 메서드/ 페이지 이동, 뒤로 가기, 앞으로 가기, 새로 고침과 같은 탐색 작업을 수행
					// back() : Navigation 인터페이스의 메서드/ 뒤로 가기
					// 브라우저의 이전 페이지로 이동

					// 페이지가 완전히 로드될 때까지 대기
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(boardElement)));

				}catch (Exception e) {
					// 오류가 발생시 다음으로 넘어감
					System.err.println("1316 로그 오류 발생: [" +i+"]");
				}
			}
		}catch (Exception e) {
			System.err.println("1320 로그 게시판 클릭 오류 발생: [" + e.getMessage()+"]");
		}

		System.out.println(" model.common.CrawlingSelenium.makeSampleBoard 종료");

		// 드라이버 종료
		//driver.quit();
		// quit() : WebDriver 클래스의 메서드/ 현재의 WebDriver 세션을 종료
		return board;			

	}

	//메인 메서드로 테스트
	//	public static void main(String[] args) {
	//		CrawlingSelenium.makeSampleBoard();
	//	}
}

