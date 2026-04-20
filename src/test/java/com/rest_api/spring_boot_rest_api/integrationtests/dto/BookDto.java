package com.rest_api.spring_boot_rest_api.integrationtests.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BookDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String author;

    private Date date;

    private Float price;

    private String title;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id) && Objects.equals(author, bookDto.author) && Objects.equals(date, bookDto.date) && Objects.equals(price, bookDto.price) && Objects.equals(title, bookDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, date, price, title);
    }

    public BookDto() {
    }

    public BookDto(Long id, String author, Date date, Float price, String title) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.price = price;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
