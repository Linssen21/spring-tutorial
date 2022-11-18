import { STUDENTS } from '../actions/types';
import { removeObjectFromArray } from '../../utils/arrayObjectHelper';

export const STUDENTS_STATE = {
    students: [],
    student: {},
    error: {},
};

export const StudentsReducer = (state = STUDENTS_STATE, action) => {
    switch (action.type) {
        case STUDENTS.FETCH:
            return {
                ...state,
                students: action.data,
            };
        case STUDENTS.ADD:
            return addStudent(state, action);
        case STUDENTS.DELETE:
            return deleteStudent(state, action);
        case STUDENTS.ERROR:
            return errorStudents(state, action);
        default:
            return state;
    }
};

const errorStudents = (state, action) => {
    return {
        ...state,
        error: action.data,
    };
};

const addStudent = (state, action) => {
    const currentStudents = state.students;
    const generateId =
        Math.max(...currentStudents.map((student) => student.id)) + 1;
    const addedStudent = Object.assign({ id: generateId }, action.data);
    console.log(state);
    return {
        error: {},
        students: [addedStudent, ...state.students],
    };
};

const deleteStudent = (state, action) => {
    const currentStudents = state.students;
    const studentIdToDelete = action.data;

    const students = removeObjectFromArray(
        currentStudents,
        'id',
        studentIdToDelete,
    );

    return {
        students,
        error: {},
    };
};
