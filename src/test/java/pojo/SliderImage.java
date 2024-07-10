package pojo;

public class SliderImage {

	private String nextSlideButtonLocator = "//*[text()='Next slide']/ancestor::i";
	private String imageContainerLocator = "//*[@id='anonCarousel1']/descendant::li/descendant::img";

	public String getNextSlideButtonLocator() {
		return nextSlideButtonLocator;
	}

	public String getImageContainerLocator() {
		return imageContainerLocator;
	}

}
