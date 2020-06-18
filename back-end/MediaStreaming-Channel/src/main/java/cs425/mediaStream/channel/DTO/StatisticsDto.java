package cs425.mediaStream.channel.DTO;

import java.time.LocalDate;

public class StatisticsDto {
	
	private String date;
	private long number;
	public StatisticsDto(String date, long number) {
		this.date = date;
		this.number = number;
	}
	public StatisticsDto() {
	
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}


	
	

}
