using UnityEngine;

public class Weapon : MonoBehaviour
{
    public float dropForce = 5f; // 떨어질 때의 힘

    void Start()
    {
        // 초기 속도 설정 (옵션)
        Rigidbody2D rb = GetComponent<Rigidbody2D>();
        if (rb != null)
        {
            rb.AddForce(Vector2.up * dropForce, ForceMode2D.Impulse);
        }
    }
}
