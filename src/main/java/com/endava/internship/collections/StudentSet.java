package com.endava.internship.collections;

import java.util.*;

public class StudentSet implements Set<Student>, Iterable<Student> {
    private Node root;
    private int size;
    private Collection<? extends Student> collection;

    public StudentSet() {
        root = null;
        size = 0;
    }

    StudentSet(Collection<? extends Student> collection) {
        addAll(collection);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public Node getRoot() {
        return root;
    }

    public Student getRootValue() {
        return root.value;
    }

    @Override
    public boolean contains(Object o) {
        Node current = root;
        while (current != null) {
            int compareResult = ((Student) o).compareTo(current.value);
            if (compareResult == 0) {
                return true;
            }
            if (compareResult < 0) {
                current = current.left;
            }
            if (compareResult > 0) {
                current = current.right;
            }
        }
        return false;
    }


    @Override
    public Iterator<Student> iterator() {
        return new StudentIterator<>();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        Iterator<Student> iterator = new StudentIterator<>();
        int i = 0;
        while (iterator.hasNext()) {
            result[i++] = iterator.next();
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        Iterator<Student> iterator = new StudentIterator<>();
        int i = 0;
        if (ts[0] instanceof Student) {
            while (iterator.hasNext()) {
                ts[i++] = (T) iterator.next();
            }
        } else {
            throw new RuntimeException("The array is not type of Student");
        }
        return ts;
    }

    @Override
    public boolean add(Student student) {
        if (student == null) {
            return false;
        }
        if (root == null) {
            size++;
            root = new Node(student);
            return true;
        }
        Node current = root;
        while (true) {
            int compareResult = student.compareTo(current.value);
            if (compareResult == 0) {
                return false;
            } else if (compareResult < 0) {
                if (current.left == null) {
                    Node addNode = new Node(student);
                    current.left = addNode;
                    addNode.parent = current;
                    size++;
                    return true;
                } else {
                    current = current.left;
                }
            } else if (current.right == null) {
                Node addNode = new Node(student);
                current.right = addNode;
                addNode.parent = current;
                size++;
                return true;
            } else {
                current = current.right;
            }
        }
    }

    private boolean addCheck(Student student) {
        if (student == null) {
            return false;
        }
        if (root == null) {
            size++;
            root = new Node(student);
            return true;
        }
        Node current = root;
        while (current != null) {
            int compareResult = student.compareTo(current.value);
            if (compareResult == 0) {
                return false;
            } else if (compareResult < 0) {
                if (current.left == null) {
                    return true;
                }
                current = current.left;
            } else if (current.right == null) {
                return true;
            }
            current = current.right;
        }
        return false;
    }

    private Node getNode(Student student) {
        if (root == null) {
            return null;
        }
        Node current = root;
        while (current != null) {
            int compareResult = student.compareTo(current.value);
            if (compareResult == 0) {
                return current;
            } else if (compareResult < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    private void deleteNodeWithoutHeirs(Node node) {
        Node parentNode = node.parent;
        if (parentNode.left == node) {
            parentNode.left = null;
        } else {
            parentNode.right = null;
        }
    }

    private void deleteNodeWithHeirs(Node node) {
        Node smallest = searchSmallestOfRightSubtree(node);
        if (smallest.right != null) {
            node.value = smallest.value;
            deleteNodeByRightSubTreePresence(smallest, true);
        } else {
            node.value = smallest.value;
            deleteNodeWithoutHeirs(smallest);
        }
    }

    private void deleteNodeByRightSubTreePresence(Node node, Boolean rightSubTreePresence) {
        Node parentNode = node.parent;
        if (rightSubTreePresence) {
            if (parentNode.left == node) {
                parentNode.left = node.right;
            } else {
                parentNode.right = node.right;
            }
        } else {
            if (parentNode.left == node) {
                parentNode.left = node.left;
            } else {
                parentNode.right = node.left;
            }
        }
    }

    private void deleteRoot() {
        Node smallest = searchSmallestOfRightSubtree(root);
        if (smallest.right != null) {
            root.value = smallest.value;
            deleteNodeByRightSubTreePresence(smallest, true);
        } else {
            root.value = smallest.value;
            deleteNodeWithoutHeirs(smallest);
        }
    }

    private Node searchSmallestOfRightSubtree(Node node) {
        Node result = node.right;
        while (result.left != null) {
            result = result.left;
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        Node current = getNode((Student) o);
        if (current == null) {
            return false;
        }
        if (root == current) {
            deleteRoot();
        } else if (current.left == null & current.right == null) {
            deleteNodeWithoutHeirs(current);
        } else if (current.left != null & current.right != null) {
            deleteNodeWithHeirs(current);
        } else {
            deleteNodeByRightSubTreePresence(current, current.right != null);
        }
        return true;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean addAll(Collection<? extends Student> collection) {
        LinkedList<Boolean> check = new LinkedList<>();
        for (Student s : collection) {
            check.add(this.addCheck(s));
        }
        for (Boolean b : check) {
            if (!b) {
                return false;
            }
        }
        for (Student s : collection) {
            this.add(s);
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        LinkedList<Boolean> check = new LinkedList<>();
        for (Object o : collection) {
            check.add(this.contains(o));
        }
        for (Boolean b : check)
            if (!b) {
                return false;
            }
        return true;
    }

    public boolean containsAll(Object[] array) {
        LinkedList<Boolean> check = new LinkedList<>();
        for (Object o : array) {
            check.add(this.contains(o));
        }
        for (Boolean b : check)
            if (!b) {
                return false;
            }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (this.containsAll(collection)) {
            Object[] array = new Object[collection.size()];
            int i = 0;
            for (Object o : collection) {
                array[i++] = o;
            }
            this.clear();
            for (Object o : array) {
                this.add((Student) o);
            }
            return true;
        } else
            return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (this.containsAll(collection)) {
            for (Object o : collection) {
                this.remove(o);
            }
            return true;
        } else
            return false;
    }


    static private class Node {
        private Node left;
        private Node right;
        private Node parent;
        Student value;

        public Node(Student value) {
            this.value = value;
        }
    }

    private class StudentIterator<E extends Student> implements Iterator<Student> {
        ArrayList<E> students;
        int current = 0;

        StudentIterator() {
            if (!isEmpty()) {
                students = new ArrayList<>();
                postorder(root);
            } else {
                throw new RuntimeException("Set is empty");
            }

        }

        private void postorder(Node node) {
            if (node != null) {
                postorder(node.left);
                postorder(node.right);
                students.add((E) node.value);
            }
            if (node == root)
                students.add((E) node.value);
        }

        @Override
        public boolean hasNext() {
            return current + 1 < students.size() &&
                    students.get(current + 1) != null;
        }

        @Override
        public Student next() {
            return students.get(current++);
        }
    }
}
