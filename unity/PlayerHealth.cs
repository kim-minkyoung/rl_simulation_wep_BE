using UnityEngine;

public class PlayerHealth : MonoBehaviour
{
    public int maxHealth = 5; // 최대 체력
    private int currentHealth; // 현재 체력

    void Start()
    {
        currentHealth = maxHealth; // 현재 체력을 최대 체력으로 초기화
    }

    public void TakeDamage(int damage)
    {
        currentHealth -= damage; // 체력 감소
        Debug.Log("Player damaged! Current health: " + currentHealth);

        if (currentHealth <= 0)
        {
            Die(); // 죽는 메서드 호출
        }
    }

    void Die()
    {
        Debug.Log("Player died!");
        // 게임 오버 로직 추가 (예: 재시작, 종료 등)
        Destroy(gameObject); // 플레이어 제거
    }
}
