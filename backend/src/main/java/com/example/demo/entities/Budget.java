package com.example.demo.entities;

import java.time.LocalDate;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "budgets")
public class Budget {
    enum Period
    {
        NONE,
        DAILY,
        WEEKLY,
        MONTHLY,
        QUARTERLY,
        BIANNUAL,
        ANNUAL
    }
    
    @Id
    private ObjectId id;
    private Period reset_period_type;
    private LocalDate reset_deadline;
    private ObjectId user_id;
    private String name;


    /* CONSTRUCTORS */
    public Budget()
    {
        setID(null);
        setResetPeriodType(Period.NONE);
        setResetDeadline(null);
        setUserID(null);
        setName("");
    }

    @JsonCreator
    public Budget( String name, @JsonProperty("reset_period_type") Period resetPeriodType, @JsonProperty("reset_deadline") LocalDate resetDeadline, @JsonProperty("user_id") ObjectId userId) {
        setResetPeriodType(resetPeriodType);
        setResetDeadline(resetDeadline);
        setUserID(userId);
        setName(name);
    }

    /* GETTERS */
    public final ObjectId getID()
    {
        return id;
    }

    public final Period getResetPeriodType()
    {
        return reset_period_type;
    }

    public final LocalDate getResetDeadline()
    {
        return reset_deadline;
    }

    public final ObjectId getUserID()
    {
        return user_id; 
    }

    public final String getName()
    {
        return name; 
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

    public boolean setResetPeriodType(Period reset_period_type)
    {
        if(Objects.isNull(reset_period_type))
        {
            return false;
        }
        this.reset_period_type = reset_period_type;
        return true;
    }

    public boolean setResetDeadline(LocalDate reset_deadline)
    {
        if(Objects.isNull(reset_deadline) || reset_deadline.isBefore(LocalDate.now()) || reset_deadline.isAfter(LocalDate.now().plusYears(1).plusDays(1)))
        {
            return false;
        }
        this.reset_deadline = reset_deadline;
        return true;
    }

    public boolean setUserID(ObjectId user_id)
    {
        if(Objects.isNull(user_id))
        {
            return false;
        }
        this.user_id = user_id;
        return true;
    }

    public boolean setName(String name)
    {
        if(Objects.isNull(name) || name.isBlank() || name.length() > 100)
        {
            return false;
        }
        this.name = name;
        return true;
    }

}
