package com.example.demo.entities;

import java.time.LocalDate;
import java.util.Objects;

import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "expenses") 
public class Expense {
    @Id
    private ObjectId id;
    private Decimal128 amount;
    private String comment;
    private LocalDate date_entered;
    private ObjectId category_id;

    /* CONSTRUCTORS */
    
    public Expense()
    {
        setID(null);
        setAmount(null);
        setComment("");
        setDateEntered(null);
        setCategoryID(null);
    }

    @JsonCreator
    public Expense(@JsonProperty("amount") Decimal128 amount, @JsonProperty("comment") String comment, @JsonProperty("date_entered") LocalDate date_entered, @JsonProperty("category_id") ObjectId category_id)
    {
        setAmount(amount);
        setComment(comment);
        setDateEntered(date_entered);
        setCategoryID(category_id);
    }

    /* GETTERS */
    public ObjectId getID()
    {
        return id;
    }

    public Decimal128 getAmount()
    {
        return amount;
    }

    public String getComment()
    {
        return comment;
    }

    public LocalDate getDateEntered()
    {
        return date_entered;
    }

    public ObjectId getCategoryID()
    {
        return category_id;
    }

    /* SETTERS */
    public boolean setID(ObjectId id) {
        if(Objects.isNull(id))
        {
            return false;
        }
        this.id = id;
        return true;
    }
    public boolean setAmount(Decimal128 amount)
    {
        if(Objects.isNull(amount) || amount.isNegative() || amount.isInfinite() || amount.equals(Decimal128.NaN))
        {
            return false;
        }
        this.amount = amount;
        return true;
    }

    public boolean setComment(String comment)
    {
        if(Objects.isNull(comment) || (comment.isBlank() && !comment.isEmpty()) || comment.length() > 500)
        {
            return false;
        }
        this.comment = comment;
        return true;
    }

    public boolean setDateEntered(LocalDate date_entered)
    {
        if(Objects.isNull(date_entered) || date_entered.isBefore(LocalDate.now()) || date_entered.isAfter(LocalDate.now()))
        {
            return false;
        }
        this.date_entered = date_entered;
        return true;
    }

    public boolean setCategoryID(ObjectId category_id)
    {
        if(Objects.isNull(category_id))
        {
            return false;
        }
        this.category_id = category_id;
        return true;
    }

}