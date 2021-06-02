package UniProject.model;

public class Grade {
    private long id;
    private Subject subject;
    private int score;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", subject=" + subject +
                ", score=" + score +
                '}';
    }
}
