package com.wechat.common.pojo.image;

import com.wechat.common.pojo.BaseMessage;

public class ImageMessage extends BaseMessage {
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
}
