package UniProject.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Application {
    private long id;
    private User applicant;
    private Faculty faculty;
    private ApplicationStatus status;
    private TypeOfEducation typeOfEducation;
    private List<Grade> grades;

    public long getId() {
        return id;
    }

    public Application setId(long id) {
        this.id = id;
        return this;
    }

    public User getApplicant() {
        return applicant;
    }

    public Application setApplicant(User applicant) {
        this.applicant = applicant;
        return this;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Application setFaculty(Faculty faculty) {
        this.faculty = faculty;
        return this;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public Application setStatus(ApplicationStatus status) {
        this.status = status;
        return this;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public Application setGrades(List<Grade> grades) {
        this.grades = grades;
        return this;
    }

    public TypeOfEducation getTypeOfEducation() {
        return typeOfEducation;
    }

    public Application setTypeOfEducation(TypeOfEducation typeOfEducation) {
        this.typeOfEducation = typeOfEducation;
        return this;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", applicant=" + applicant +
                ", faculty=" + faculty +
                ", status=" + status +
                ", typeOfEducation=" + typeOfEducation +
                ", grades=" + grades +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id && Objects.equals(applicant, that.applicant) && Objects.equals(faculty, that.faculty) && status == that.status && typeOfEducation == that.typeOfEducation && Objects.equals(grades, that.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applicant, faculty, status, typeOfEducation, grades);
    }

    public static final Comparator<Application> EMAIL_COMPARATOR =
            Comparator.comparing((Application a) -> a.applicant.getEmail())
                    .thenComparing(Application::getStatus);
    public static final Comparator<Application> FACULTY_COMPARATOR = (Comparator.comparing((Application a) -> a.faculty.getName()))
            .thenComparing(Application::getStatus);
}
