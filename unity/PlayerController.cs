using UnityEngine;

public class PlayerController : MonoBehaviour
{
    public float moveSpeed = 5f; // 기본 이동 속도
    public float runSpeed = 10f; // 달리기 속도
    public float jumpForce = 10f; // 점프 힘
    public float rollDuration = 0.5f; // 구르기 지속 시간
    public float climbSpeed = 3f; // 벽타기 속도
    public LayerMask groundLayer; // 땅 레이어
    public LayerMask wallLayer; // 벽 레이어
    public GameObject attackCollider; // 공격 콜라이더 프리팹
    private Rigidbody2D rb; // Rigidbody2D 컴포넌트
    private bool isGrounded; // 땅에 닿아 있는지 여부
    private bool isRolling; // 구르기 중인지 여부
    private bool isClimbing; // 벽타기 중인지 여부
    private bool isAttacking; // 공격 중인지 여부
    private float rollTimer; // 구르기 타이머
    private PlayerHealth playerHealth; // 플레이어 체력 스크립트 참조

    void Start()
    {
        rb = GetComponent<Rigidbody2D>(); // Rigidbody2D 컴포넌트 초기화
        attackCollider.SetActive(false); // 공격 콜라이더 비활성화
        playerHealth = GetComponent<PlayerHealth>(); // PlayerHealth 컴포넌트 초기화
    }

    void Update()
    {
        if (!isClimbing)
        {
            Move();
            Jump();
            Roll();
            Attack();
            Defend();
            Parry();
        }
        else
        {
            Climb();
        }
    }

void Move()
    {
        if (!isRolling && !isAttacking)
        {
            float moveInput = Input.GetAxis("Horizontal"); // 좌우 입력 받기
            float speed = Input.GetKey(KeyCode.LeftShift) ? runSpeed : moveSpeed; // Shift 키로 달리기
            rb.velocity = new Vector2(moveInput * speed, rb.velocity.y); // 이동 적용
        }
    }

    void Jump()
    {
        if (Input.GetButtonDown("Jump") && isGrounded)
        {
            rb.AddForce(Vector2.up * jumpForce, ForceMode2D.Impulse); // 점프 힘 추가
        }
    }

    void Roll()
    {
        if (Input.GetButtonDown("Fire1") && !isRolling && !isAttacking)
        {
            isRolling = true;
            rb.velocity = new Vector2(rb.velocity.x * 2, rb.velocity.y); // 구르기 속도 적용
            rollTimer = rollDuration; // 타이머 시작
        }

        if (isRolling)
        {
            rollTimer -= Time.deltaTime;
            if (rollTimer <= 0)
            {
                isRolling = false; // 구르기 종료
            }
        }
    }

    void Climb()
    {
        float climbInput = Input.GetAxis("Vertical"); // 위아래 입력 받기
        rb.velocity = new Vector2(rb.velocity.x, climbInput * climbSpeed); // 벽타기 속도 적용

        // 벽타기 중 점프
        if (Input.GetButtonDown("Jump"))
        {
            isClimbing = false; // 벽타기 종료
            rb.AddForce(Vector2.up * jumpForce, ForceMode2D.Impulse); // 점프
        }

        // 벽타기 종료 시 조건
        if (!IsTouchingWall())
        {
            isClimbing = false; // 벽타기 종료
        }
    }

    private bool IsTouchingWall()
    {
        // 플레이어가 벽에 닿아 있는지 체크
        return Physics2D.OverlapCircle(transform.position, 0.1f, wallLayer);
    }

    void Attack()
    {
        if (Input.GetButtonDown("Fire2") && !isRolling && !isAttacking)
        {
            isAttacking = true;
            attackCollider.SetActive(true); // 공격 콜라이더 활성화
            Invoke("ResetAttack", 0.5f); // 공격 후 상태 초기화
        }
    }

    void ResetAttack()
    {
        isAttacking = false; // 공격 상태 초기화
        attackCollider.SetActive(false); // 공격 콜라이더 비활성화
    }

    void Defend()
    {
        // 방어 로직 추가 (예: 방어 애니메이션)
        if (Input.GetKey(KeyCode.Z))
        {
            // 방어 동작
            Debug.Log("Player is defending!");
        }
    }

    void Parry()
    {
        // 패링 로직 추가 (예: 적 공격을 막는 동작)
        if (Input.GetKeyDown(KeyCode.X))
        {
            // 패링 동작
            Debug.Log("Player performed a parry!");
        }
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        // 땅에 닿았을 때
        if (((1 << collision.gameObject.layer) & groundLayer) != 0)
        {
            isGrounded = true;
        }

        // 적과 충돌했을 때
        if (collision.gameObject.CompareTag("Enemy"))
        {
            // 플레이어에게 피해를 주는 로직 (예: 적의 공격)
            playerHealth.TakeDamage(1); // 피해량을 1로 설정
        }
    }


    private void OnCollisionExit2D(Collision2D collision)
    {
        // 땅에서 떨어졌을 때
        if (((1 << collision.gameObject.layer) & groundLayer) != 0)
        {
            isGrounded = false;
        }
    }

    private void OnTriggerEnter2D(Collider2D collider)
    {
        // 벽에 닿았을 때
        if (((1 << collider.gameObject.layer) & wallLayer) != 0)
        {
            isClimbing = true; // 벽타기 시작
        }
    }

    private void OnTriggerExit2D(Collider2D collider)
    {
        // 벽에서 떨어졌을 때
        if (((1 << collider.gameObject.layer) & wallLayer) != 0)
        {
            isClimbing = false; // 벽타기 종료
        }
    }


}
