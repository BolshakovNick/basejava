/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        storage = null;
    }

    //save the new element after last element
    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
            }
        }
    }

    //get the element
    Resume get(String uuid) {
        Resume resume = null;
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                resume = storage[i];
                break;
            }
        }
        return resume;
    }

    //delete element and shift other elements one left
    void delete(String uuid) {
        int count = size();
        for (int i = 0; i < count; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                for (int j = i; j < count; j++) {
                    storage[j] = storage[j + 1];
                }
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] r = new Resume[this.size()];
        for (int i = 0; i < size(); i++) {
            r[i] = storage[i];
        }
        return r;
    }

    //not null element length
    int size() {
        int size = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                size = i;
                break;
            }
        }
        return size;
    }
}
