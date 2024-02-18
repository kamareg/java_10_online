package ua.com.alevel;

public class Word {
    private String word;
    private Long rating;
    private Long count;
    private Long percentage;

    public Word(String word, Long count, Long percentage) {
        this.word = word;
        this.count = count;
        this.percentage = percentage;
        this.rating = 0L;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", rating=" + rating +
                ", count=" + count +
                ", percentage=" + percentage +
                '}';
    }
}
