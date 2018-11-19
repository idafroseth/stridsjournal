package com.fih.stridsjournal.dao;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fih.stridsjournal.model.Message;


@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, UUID> {

	Set<Message> findBySavedAtAfter(Date after);

	Set<Message> findBySentTo(String to);

	Set<Message> findBySentToAndSavedAtAfter(String to, Date after);

}
