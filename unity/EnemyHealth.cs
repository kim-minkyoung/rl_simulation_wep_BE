using UnityEngine;

public class EnemyHealth : MonoBehaviour
{
    public int maxHealth = 3; // 최대 체력
    private int currentHealth; // 현재 체력
    public GameObject weaponPrefab; // 떨어질 무기 프리팹

    void Start()
    {
        currentHealth = maxHealth; // 현재 체력을 최대 체력으로 초기화
    }

    public void TakeDamage(int damage)
    {
        currentHealth -= damage; // 체력 감소
        Debug.Log("Enemy damaged! Current health: " + currentHealth);

        if (currentHealth <= 0)
        {
            Die(); // 죽는 메서드 호출
        }
    }

    void Die()
    {
        Debug.Log("Enemy died!");
        DropWeapon(); // 무기 떨어뜨리기
        Destroy(gameObject); // 적 제거
    }

    void DropWeapon()
    {
        if (weaponPrefab != null)
        {
            // 적의 위치에 무기 생성
            GameObject weapon = Instantiate(weaponPrefab, transform.position, Quaternion.identity);
            Rigidbody2D rb = weapon.GetComponent<Rigidbody2D>();
            if (rb != null)
            {
                // 약간의 힘을 줘서 무기를 떨어뜨림
                rb.AddForce(new Vector2(Random.Range(-2f, 2f), 2f), ForceMode2D.Impulse);
            }
        }
    }
}
