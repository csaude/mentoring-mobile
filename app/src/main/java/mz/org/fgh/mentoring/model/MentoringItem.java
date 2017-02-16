package mz.org.fgh.mentoring.model;

/**
 * Created by Stélio Moiane on 10/19/16.
 */
public class MentoringItem {

    private String itemTitle;
    private int imageId;
    private ItemType itemType;

    public MentoringItem(final String itemTitle, final int imageId, final ItemType itemType) {
        this.itemTitle = itemTitle;
        this.imageId = imageId;
        this.itemType = itemType;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public int getImageId() {
        return imageId;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
