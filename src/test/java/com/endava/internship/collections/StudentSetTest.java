package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class StudentSetTest {
    private ArrayList<Student> studentArrayList = new ArrayList<>();
    /*
         Anton4
        /       \
    Anton3    Anton6
     /        /    \
   Anton2  Anton5  Anton7
     */
    @BeforeEach
    void setUp(){
        createStudentArrayList();
    }

    @Test
    void out_ShouldBeInStrictSequence() {
        Student student1 = new Student("Anton4", LocalDate.of(2002, 8, 8), "");
        Student student2 = new Student("Anton6", LocalDate.of(2002, 8, 8), "");
        Student student3 = new Student("Anton3", LocalDate.of(2002, 8, 8), "");
        Student student4 = new Student("Anton2", LocalDate.of(2002, 8, 8), "");
        Student student5 = new Student("Anton5", LocalDate.of(2002, 8, 8), "");
        Student student6 = new Student("Anton7", LocalDate.of(2002, 8, 8), "");
        StudentSet students = createEmptySet();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
        Object[] array = createObjectArray(students);
        assertThat(array)
                .containsExactly(student4,student3,student5,student6,student2,student1);


    }
    @Test
    void iterator_ShouldWorkAccordingToPrincipleLeftRightRoot(){
        StudentSet students = createSetFromCollection();
        Object[] array = createObjectArray(students);
        int i = 0;
        for (Student s : students){
            assertTrue(students.contains(array[i]));
            assertEquals(array[i++],s);
        }
    }
    @Test
     void initializingSetWithOutParameters_ShouldBeCreatedEmptySet() {
        StudentSet emptySet = createEmptySet();
        assertNull(emptySet.getRoot());
        assertEquals(0, emptySet.size());
        assertTrue(emptySet.isEmpty());
    }

    @Test
     void insertingInSetCollectionWhichContainsRepeatedValues_ShouldBeBanned() {
        StudentSet students = createSetFromCollection(studentArrayList);
        StudentSet studentSet = createEmptySet();
        studentSet.add(studentArrayList.get(0));
        studentSet.add(studentArrayList.get(2));
        studentSet.add(studentArrayList.get(4));
        assertFalse(students.addAll(studentSet));
        assertTrue(students.containsAll(studentSet));
    }

    @Test
     void insertingInSetCollectionWhichDoNotContainsRepeatedValues_IsAllowed() {
        StudentSet studentSet = createSetFromCollection();
        StudentSet emptySet = createEmptySet();
        assertTrue(emptySet.addAll(studentSet));
    }

    @Test
     void firstElement_ShouldBeRoot() {
        StudentSet emptySet = createEmptySet();
        assertTrue(emptySet.add(studentArrayList.get(0)));
        assertEquals(emptySet.getRootValue(), studentArrayList.get(0));
        assertTrue(emptySet.contains(studentArrayList.get(0)));
    }

    @Test
     void inserting_ShouldNotIncludeRepeatingAndNullValues() {
        StudentSet emptySet = createEmptySet();
        assertTrue(emptySet.add(studentArrayList.get(0)));
        assertFalse(emptySet.add(studentArrayList.get(0)));
        assertFalse(emptySet.add(null));
        assertThat(emptySet).doesNotContainNull();
        assertThat(emptySet)
                .containsOnly(studentArrayList.get(0));
    }

    @Test
     void arrayCreatedFromSet_ShouldContainsAllInSet() {
        StudentSet students = createSetFromCollection();
        Object[] array = createObjectArray(students);
        assertTrue(students.containsAll(array));
    }

    @Test
     void deletedNodeWithoutHeirs_ShouldNotBeInSet() {
        StudentSet students = createSetFromCollection(studentArrayList);
        students.remove(studentArrayList.get(1));
        assertFalse(students.contains(studentArrayList.get(1)));
    }

    @Test
     void deletingRoot_ShouldBeChangedHisValue() {
        StudentSet students = createSetFromCollection();
        if (students.getRoot() != null) {
            students.remove(students.getRootValue());
            assertNotNull(students.getRoot());
        }
    }

    /*
         Anton4
        /       \
    Anton3    Anton6
     /        /    \
   Anton2  Anton5  Anton7
     */
    @Test
     void nodeWithHeirs_ShouldBeReplacedWithSmallestOfRightSubtree() {
        StudentSet students = createSetFromCollection(studentArrayList);
        //studentArrayList.get(1)->Anton6
        assertTrue(students.contains(studentArrayList.get(1)));
        students.remove(studentArrayList.get(1));
        assertFalse(students.contains(studentArrayList.get(1)));
    }

    @Test
     void collectionRemovedFromSet_ShouldNotContainsAllInSet() {
        StudentSet students = createSetFromCollection();
        StudentSet students1 = createEmptySet();
        students1.add((Student) students.toArray()[0]);
        students1.add((Student) students.toArray()[2]);
        students1.add((Student) students.toArray()[4]);
        assertTrue(students.containsAll(students1));
        assertTrue(students.removeAll(students1));
        assertFalse(students.containsAll(students1));
    }

    @Test
     void retainingInSetElementsFromAnotherCollection_ShouldNotBeOtherElements() {
        StudentSet students = createSetFromCollection();
        StudentSet students1 = createEmptySet();
        Object[] array = createObjectArray(students);
        students1.add((Student) array[0]);
        students1.add((Student) array[2]);
        students1.add((Student) array[4]);
        assertFalse(students1.contains(array[1]));
        assertTrue(students.contains(array[1]));
        assertTrue(students.retainAll(students1));
        assertFalse(students.contains(array[1]));
    }

    private void createStudentArrayList() {
        studentArrayList.add(new Student("Anton4", LocalDate.of(2002, 8, 8), ""));
        studentArrayList.add(new Student("Anton6", LocalDate.of(2002, 8, 8), ""));
        studentArrayList.add(new Student("Anton3", LocalDate.of(2002, 8, 8), ""));
        studentArrayList.add(new Student("Anton2", LocalDate.of(2002, 8, 8), ""));
        studentArrayList.add(new Student("Anton5", LocalDate.of(2002, 8, 8), ""));
        studentArrayList.add(new Student("Anton7", LocalDate.of(2002, 8, 8), ""));
    }

    private StudentSet createSetFromCollection(ArrayList<Student> studentArrayList) {
        return new StudentSet(studentArrayList);
    }

    private StudentSet createSetFromCollection() {
        return new StudentSet(studentArrayList);
    }

    private StudentSet createEmptySet() {
        return new StudentSet();
    }

    private Object[] createObjectArray(StudentSet students) {
        return students.toArray();
    }
}