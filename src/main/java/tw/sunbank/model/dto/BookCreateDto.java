package tw.sunbank.model.dto;

public class BookCreateDto {


//	書名
	private String name;

//	作者
	private String author;

//	書籍內容簡介
	private String introduction;

	public BookCreateDto() {
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}


	@Override
	public String toString() {
		return "BookCreateDto [name=" + name + ", author=" + author + ", introduction=" + introduction + "]";
	}


}
