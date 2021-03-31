package com.epam.EpamUniversityProject.model;

import com.epam.EpamUniversityProject.repository.dao.impl.ApplicationDaoPostgreImpl;
import com.epam.EpamUniversityProject.repository.dao.impl.FacultyDaoPostgresImpl;
import com.epam.EpamUniversityProject.repository.dao.interfaces.ApplicationDao;
import com.epam.EpamUniversityProject.repository.dao.interfaces.FacultyDao;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * class to create final statement and state if user applied or not
 **/


public class Statement {
    private List<Faculty> faculties;
    private List<Application> applications;

    public Statement() {
    }

    public Statement(List<Faculty> faculties, List<Application> applications) {
        this.faculties = faculties;
        this.applications = applications;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }


    /**
     * assign faculty for each applicant
     *
     * @return list of applications, which are either applied or not
     **/
    public List<Application> makeFinalStatement() {
        for (Faculty faculty : faculties) {
            processApplicationsForFaculty(faculty, TypeOfEducation.STATE_FUNDED);
            processApplicationsForFaculty(faculty, TypeOfEducation.PAID);
        }
        applications.stream()
                .filter(
                        application -> application.getStatus().equals(ApplicationStatus.NOT_PROCEED)
                ).forEach(application -> application.setStatus(ApplicationStatus.NOT_APPLIED));
        return applications;
    }

    /**
     * assign applicants for specific faculty
     *
     * @param faculty - faculty to which applicants assign
     * @param type    type of education
     **/
    private void processApplicationsForFaculty(Faculty faculty, TypeOfEducation type) {
        int numOfPlaces;
        AtomicInteger counter = new AtomicInteger(0);
        if (type.equals(TypeOfEducation.STATE_FUNDED)) {
            numOfPlaces = faculty.getBudgetPlaces();
        } else {
            numOfPlaces = faculty.getTotalPlaces() - faculty.getBudgetPlaces();
        }

//        List<Application> tmp =
        applications.stream()
                .filter(application -> application.getFaculty().equals(faculty)
                        && application.getStatus().equals(ApplicationStatus.NOT_PROCEED)
                        && application.getTypeOfEducation().equals(type))
                .sorted(Comparator.comparingInt(this::getApplicationGradesAvg).reversed())
                .limit(numOfPlaces)
                .forEachOrdered(application -> {
                    application.setStatus(ApplicationStatus.APPLIED);
                    removeRedundantApplications(application);
                    System.out.println("asign:"+application.getStatus() + " " + getApplicationGradesAvg(application) + " " + application.getApplicant().getEmail());
                });
    }

    /**
     * remove other applications if one application is applied:
     * firstly find all other applications of specific applicant except applied one
     * * then set status NOT_APPLIED for found applications
     *
     * @param appliedApplication applied application
     * @param appliedApplication application for which we need to remove other applications
     *                           sent by its applicant
     */
    private void removeRedundantApplications(Application appliedApplication) {
        applications.stream().filter(
                application -> application.getApplicant().equals(appliedApplication.getApplicant())
                        && !application.equals(appliedApplication)
        ).forEach(application -> {
            System.out.println("remove"+application.getApplicant().getEmail()+" "+application.getId());
            application.setStatus(ApplicationStatus.NOT_APPLIED);
        });
    }

    /**
     * @param appliedApplications list of applied applications for which we need to remove other applications
     *                            sent by its applicant
     */
    private void removeAllRedundantApplications(List<Application> appliedApplications) {
        for (Application appliedApplication : appliedApplications) {
            removeRedundantApplications(appliedApplication);
        }
    }

    private int getApplicationGradesAvg(Application application) {
        int applicationGradesSum = 0;
        for (Grade grade : application.getGrades()) {
            applicationGradesSum += grade.getScore();
        }
        return applicationGradesSum/application.getGrades().size();
    }
}
