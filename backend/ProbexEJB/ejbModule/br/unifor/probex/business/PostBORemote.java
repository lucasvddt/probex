package br.unifor.probex.business;

import java.util.List;

import javax.ejb.Remote;

import br.unifor.probex.dto.VoteDTO;
import br.unifor.probex.entity.Post;

@Remote
public interface PostBORemote {

	public List<Post> listPosts(int quantity, int start, String criteria,
                                String keywords);
	
	public List<Post> listPosts(String orderBy);
	
	public List<Post> listPosts(int quantity);
	
	public List<Post> listPosts(String orderBy, int quantity);

	public List<Post> searchKeywords(List<String> keywords);
	
	public List<Post> searchKeywords(List<String> keywords, String orderBy);
	
	public List<Post> searchKeywords(List<String> keywords, int quantity);
	
	public List<Post> searchKeywords(List<String> keywords, String orderBy, int quantity);

	public Post addPost(Post post);

	public Post findPostById(Long id);

	public String removePost(Long id);

	public String updatePost(Post post);

	public String voteOnPost(VoteDTO vote);
}
