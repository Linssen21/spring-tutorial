import { STUDENTS } from './types';

export const fetchStudents = (students) => {
    return {
        type: STUDENTS.FETCH,
        data: students,
    };
};

export const addStudent = (student) => {
    return {
        type: STUDENTS.ADD,
        data: student,
    };
};

export const deleteStudent = (id) => {
    return {
        type: STUDENTS.DELETE,
        data: id,
    };
};

export const errorStudents = (error) => {
    return {
        type: STUDENTS.ERROR,
        data: error,
    };
};
