package kr.spring.board.vo;

public class BoardFavVO {
	private long board_num;
	private long mem_num;
	
	public long getBoard_num() {
		return board_num;
	}
	public void setBoard_num(long board_num) {
		this.board_num = board_num;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	@Override
	public String toString() {
		return "BoardFavVO [board_num=" + board_num + ", mem_num=" + mem_num + "]";
	}
}
