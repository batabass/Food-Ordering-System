package foodordering.demo.domain;

public class Menuitem {
    
    private String itemName;
    private Double price;
    private int preparationTime;

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public void setPreparationTime(int preparationTime)
    {
        this.preparationTime = preparationTime;
    }

    public String getItemName()
    {
        return this.itemName;
    }

    public Double getPrice()
    {
        return this.price;
    }

    public int getPreparationTime()
    {
        return this.preparationTime;
    }


}
