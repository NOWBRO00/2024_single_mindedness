$(document).ready(function() {
	function checkImage() {
		var fileInput = document.getElementById('profile');
		var file = fileInput.files[0];
		var fileMsg = document.getElementById('fileMsg');
		
		           
		        

		// 파일 확장자 검사
		if (file) {
			var fileName = file.name.toLowerCase();
			var validExtensions = ['jpg', 'jpeg', 'png'];

			// 확장자를 추출하고 유효한 확장자인지 확인
			var fileExtension = fileName.split('.').pop();
			if (!validExtensions.includes(fileExtension)) {
				fileMsg.innerText = "jpg, jpeg, png 파일만 업로드할 수 있습니다.";
				fileInput.value = ""; // 잘못된 파일은 초기화
				console.log("1.fileName : " + fileName);
				console.log("1.fileExtension : " + fileExtension);
				// 잘못된 파일이 있을 경우 버튼 비활성화
				document.getElementById('imageSubmitBtn').disabled = true;
			} else {
				fileMsg.innerText = fileName; // 선택한 파일 이름 보여주기
				fileMsg.style.color="black";	
				console.log("2.fileName : " + fileName);
				console.log("2.fileExtension : " + fileExtension);
				// 정상 파일일 경우 버튼 활성화
				document.getElementById('imageSubmitBtn').disabled = false;
				
			}
		}
	}

	$('#profile').on('change', function() {
		checkImage();
	});
	
	
})