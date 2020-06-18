package cs425.mediaStream.video.clientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UploadVideoService {
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${upload.video}")
	private String uploadVideo;
	
	@Value("${detect.Video}")
	private String detectVideo;
	
	public void uploadVideo(String videoName) {
		restTemplate.getForEntity(uploadVideo+"/"+videoName, String.class);
	}
	
	public void detectVideoObjects(String videoName) {
		restTemplate.getForEntity(detectVideo+"/"+videoName, String.class);
	}
}
