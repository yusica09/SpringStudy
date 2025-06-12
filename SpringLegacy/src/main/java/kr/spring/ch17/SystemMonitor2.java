package kr.spring.ch17;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SystemMonitor2 {
	@Autowired
	@Qualifier("main") 
	private Recorder recorder;

	public void setRecorder(Recorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public String toString() {
		return "SystemMonitor2 [recorder=" + recorder + "]";
	}
	
}
