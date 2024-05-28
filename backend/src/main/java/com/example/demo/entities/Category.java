package com.example.demo.entities;

import java.util.Objects;

import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "categories")
public class Category {
    @Id
    private ObjectId id;
    private Decimal128 amount_allocated;
    private String name;
    private String description;
    private ObjectId budget_id;

    /* CONSTRUCTORS */

    public Category()
    {
        setID(null);
        setAmountAllocated(null);
        setName("");
        setDescription("");
        setBudgetID(null);
    }

    @JsonCreator
    public Category(@JsonProperty("amount_allocated") Decimal128 amount_allocated, @JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("budget_id") ObjectId budget_id)
    {
        setAmountAllocated(amount_allocated);
        setName(name);
        setDescription(description);
        setBudgetID(budget_id);
    }

    /* GETTERS */
    public ObjectId getID()
    {
        return id;
    }

    public Decimal128 getAmountAllocated()
    {
        return amount_allocated;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public ObjectId getBudgetID()
    {
        return budget_id;
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

    public boolean setAmountAllocated(Decimal128 amount_allocated)
    {
        if(Objects.isNull(amount_allocated) || amount_allocated.isNegative() || amount_allocated.isInfinite() || amount_allocated.equals(Decimal128.NaN))
        {
            return false;
        }
        this.amount_allocated = amount_allocated;
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

    public boolean setDescription(String description)
    {
        if(Objects.isNull(description) || (description.isBlank() && !description.isEmpty()) || description.length() > 500)
        {
            return false;
        }
        this.description = description;
        return true;
    }

    public boolean setBudgetID(ObjectId budget_id)
    {
        if(Objects.isNull(budget_id))
        {
            return false;
        }
        this.budget_id = budget_id;
        return true;
    }

}