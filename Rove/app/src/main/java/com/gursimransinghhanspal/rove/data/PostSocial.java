package com.gursimransinghhanspal.rove.data;

import java.io.Serializable;
import java.util.ArrayList;

public class PostSocial implements Serializable {
	public Integer numLikes = 0;
	public Integer numBookmarks = 0;
	public Integer numComments = 0;
	public ArrayList<String> comments = new ArrayList<>();

	public Boolean doesUserLike = false;
	public Boolean didUserBookmark = false;
	public Boolean didUserComment = false;
}
