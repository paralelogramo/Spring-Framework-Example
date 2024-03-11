package com.backend.backend.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.backend.backend.entities.Article;
import com.backend.backend.entities.Author;
import com.backend.backend.entities.Conference;
import com.backend.backend.entities.Edition;
import com.backend.backend.entities.Researcher;
import com.backend.backend.entities.User;
import com.backend.backend.payloads.ArticleDTO;
import com.backend.backend.payloads.AuthorDTO;
import com.backend.backend.payloads.ConferenceDTO;
import com.backend.backend.payloads.EditionDTO;
import com.backend.backend.payloads.ResearcherDTO;
import com.backend.backend.payloads.UserDTO;

public class Transformations {

    @Autowired
    private static BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private static ModelMapper modelMapper = new ModelMapper();
    
    public static Researcher dtoToResearcher(ResearcherDTO rDto) {
        return modelMapper.map(rDto, Researcher.class);
    }

    public static ResearcherDTO researcherToDTO(Researcher r) {
        return modelMapper.map(r, ResearcherDTO.class);
    }

    public static Conference dtoToConference(ConferenceDTO cDto) {
        return modelMapper.map(cDto, Conference.class);
    }

    public static ConferenceDTO conferenceToDTO(Conference c) {
        return modelMapper.map(c, ConferenceDTO.class);
    }

    public static Article dtoToArticle(ArticleDTO aDto) {
        return modelMapper.map(aDto, Article.class);
    }

    public static ArticleDTO articleToDTO(Article a) {
        return modelMapper.map(a, ArticleDTO.class);
    }

    public static Edition dtoToEdition(EditionDTO eDto) {
        return modelMapper.map(eDto, Edition.class);
    }

    public static EditionDTO editionToDTO(Edition e) {
        return modelMapper.map(e, EditionDTO.class);
    }

    public static Author dtoToAuthor(AuthorDTO aDto) {
        return modelMapper.map(aDto, Author.class);
    }

    public static AuthorDTO authorToDTO (Author a) {
        return modelMapper.map(a, AuthorDTO.class);
    }

    public static User DTOToUser(UserDTO userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    public static UserDTO userToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
