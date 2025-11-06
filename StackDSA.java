import java.util.ArrayList;

public class StackDSA<T> {
    private ArrayList<T> stack = new ArrayList<>();

    public void push(T data) {
        stack.add(data);
    }

    public T pop() {
        if(stack.isEmpty()) throw new RuntimeException("Stack is empty");
        return stack.remove(stack.size() - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public ArrayList<T> getAll() {
        return new ArrayList<>(stack); // return all elements
    }
}
