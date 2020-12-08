package br.com.wallace.javatwitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wallace.javatwitter.model.TweetResumo;

public interface TwitterRepository extends JpaRepository<TweetResumo, Long> {

}
